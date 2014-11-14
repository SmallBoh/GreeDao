package Apk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class Apk {
	//http://www.cnblogs.com/yaozhongxiao/p/3523061.html
	//http://blog.csdn.net/t12x3456/article/details/7957117
	private Project project;
	private final static String projectBasePath="F://android/androidItem/MS";//Ҫ����ĸ�Ŀ¼
	private final static String copyApkPath="F://";//���������apk��Ŀ¼
	private final static String signApk = "Project-MS.apk";// ������ļ���������׼ȷ����Ŀ��������Project���̵�binĿ¼�����apk��װ��������   
	private final static String reNameApk = "Project_";// ������֮��d��Ŀ����ǰ׺(��ͼ��Ŀ��
	private final static String placeHolder = "@market@";// ��Ҫ�޸�manifest�ļ��ĵط�(ռλ��)  
	public static void main(String[] args){
		long startTime = 0L;  
		long endTime = 0L;  
		long totalTime = 0L;  
		Calendar date = Calendar.getInstance();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");  
		try {  
			System.out.println("---------ant�����Զ��������ʼ----------");  
			startTime = System.currentTimeMillis();  
			date.setTimeInMillis(startTime);  
			System.out.println("��ʼʱ��Ϊ:" + sdf.format(date.getTime()));  

			BufferedReader br = new BufferedReader(new FileReader("F://android/androidItem/JavaGreeDao/src/libs/market.txt"));  
			String flag = null																																	;  
			while ((flag = br.readLine()) != null) {  
				// ���޸�manifest�ļ�:��ȡ��ʱ�ļ��е�@market@�޸�Ϊ�г���ʶ,Ȼ��д��manifest.xml��   
				String tempFilePath = projectBasePath + File.separator  
						+ "AndroidManifest.xml.temp";  

				String filePath = projectBasePath + File.separator 
						+ "AndroidManifest.xml";  
				System.out.println(projectBasePath+"\\"+"AndroidManifest.xml.temp");
				write(filePath, read(tempFilePath, flag.trim()));  ////
				// ִ�д������   
				Apk mytest = new Apk();  
				mytest.init(projectBasePath + File.separator + "build.xml",  /////
						projectBasePath);  
				mytest.runTarget("clean");  
				mytest.runTarget("release");  
				// �������ִ���������ӿ�������   
				File file = new File(projectBasePath + File.separator + "bin"  
						+ File.separator + signApk);// binĿ¼��ǩ����apk�ļ�   

				File renameFile = new File(copyApkPath + File.separator  
						+ reNameApk + flag + ".apk");  
				// ������õ�apk���������ƶ���copyApkPathλ��   
				boolean renametag = file.renameTo(renameFile);  
				System.out.println("rename------>" + renametag);  
				System.out.println("file ------>" + file.getAbsolutePath());  
				System.out.println("rename------>"  
						+ renameFile.getAbsolutePath());  
			}  
			System.out.println("---------ant�����Զ����������----------");  
			endTime = System.currentTimeMillis();  
			date.setTimeInMillis(endTime);  
			System.out.println("����ʱ��Ϊ:" + sdf.format(date.getTime()));  
			totalTime = endTime - startTime;  
		} catch (Exception e) {  
			e.printStackTrace();  
			System.out.println("---------ant�����Զ�������з����쳣----------");  
			endTime = System.currentTimeMillis();  
			date.setTimeInMillis(endTime);  
			System.out.println("�����쳣ʱ��Ϊ:" + sdf.format(date.getTime()));  
			totalTime = endTime - startTime;  
		}  
	}  
	public void init(String _buildFile, String _baseDir) throws Exception {  
		project = new Project();  
		project.init();  
		DefaultLogger consoleLogger = new DefaultLogger();  
		consoleLogger.setErrorPrintStream(System.err);  
		consoleLogger.setOutputPrintStream(System.out);  
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);  
		project.addBuildListener(consoleLogger);  
		if (_baseDir == null)  
			_baseDir = new String(".");  
		project.setBasedir(_baseDir);  //////
		if (_buildFile == null)  
			_buildFile = new String(projectBasePath + File.separator  
					+ "build.xml");  
		ProjectHelper.configureProject(project, new File(_buildFile));  
	}  

	public void runTarget(String _target) throws Exception {  
		if (project == null)  
			throw new Exception(  
					"No target can be launched because the project has not been initialized. Please call the 'init' method first !");  
		if (_target == null)  
			_target = project.getDefaultTarget();  
		project.executeTarget(_target);  
	}  

	public static String read(String filePath, String replaceStr) {  
		BufferedReader br = null;  
		String line = null;  
		StringBuffer buf = new StringBuffer();  

		try {  
			// �����ļ�·����������������   
			br = new BufferedReader(new FileReader(filePath));  
			// ѭ����ȡ�ļ���ÿһ��, ����Ҫ�޸ĵ��н����޸�, ���뻺�������   
			while ((line = br.readLine()) != null) {  
				// �˴�����ʵ����Ҫ�޸�ĳЩ�е�����   
				if (line.contains(placeHolder)) {  
					line = line.replace(placeHolder, replaceStr);  
					buf.append(line);  
				} else {  
					buf.append(line);  
				}  
				buf.append(System.getProperty("line.separator"));  
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			if (br != null) {  
				try {  
					br.close();  
				} catch (IOException e) {  
					br = null;  
				}  
			}  
		}  

		return buf.toString();  
	}  

	/** 
	 * �����ݻ�д���ļ��� 
	 *  
	 * @param filePath 
	 * @param content 
	 */  
	public static void write(String filePath, String content) {  
		BufferedWriter bw = null;  

		try {  
			// �����ļ�·���������������   
			bw = new BufferedWriter(new FileWriter(filePath));  
			// ������д���ļ���   
			bw.write(content);  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			// �ر���   
			if (bw != null) {  
				try {  
					bw.close();  
				} catch (IOException e) {  
					bw = null;  
				}  
			}  
		}  
	}  
}
