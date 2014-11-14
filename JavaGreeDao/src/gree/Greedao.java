package gree;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * sqliteÊý¾Ý¿â
 * @author ²¨²¨
 *
 */
public class Greedao {
	public static void main(String[] args) throws IOException, Exception {
		Schema schema = new Schema(1, "com.example.greeDao.entity");
		addNoTe(schema);
		new DaoGenerator().generateAll(schema, "..//MS/src");
	}
	
	public static void addNoTe(Schema scheam){
		Entity note = scheam.addEntity("Note");
		note.addStringProperty("name");
		note.addStringProperty("content");
	}
}
