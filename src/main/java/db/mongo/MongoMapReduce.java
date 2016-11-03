package db.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class MongoMapReduce {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MongoClient mongo;
		try {
			mongo = new MongoClient("10.10.10.198", 27017);
			DB db = mongo.getDB("library");
			DBCollection books = db.getCollection("books");
			BasicDBObject book = new BasicDBObject();
			book.put("name", "Understanding JAVA");
			book.put("pages", 100);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding JSON");
			book.put("pages", 200);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding XML");
			book.put("pages", 300);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding Web Services");
			book.put("pages", 400);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding Axis2");
			book.put("pages", 150);
			books.insert(book);

			String map = "function() { " + "var category; "
					+ "if ( this.pages >= 250 ) " + "category = 'Big Books'; "
					+ "else " + "category = 'Small Books'; "
					+ "emit(category, {name: this.name});}";

			String reduce = "function(key, values) { " + "var sum = 0; "
					+ "values.forEach(function(doc) { " + "sum += 1; " + "}); "
					+ "return {books: sum};} ";

			MapReduceCommand cmd = new MapReduceCommand(books, map, reduce,
					null, MapReduceCommand.OutputType.INLINE, null);
			MapReduceOutput out = books.mapReduce(cmd);
			for (DBObject o : out.results()) {
				System.out.println(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mapReduce2() throws Exception {
		MongoClient mongo = new MongoClient("host", 27017);
		DB db = mongo.getDB("dbName");
		DBCollection dbc = db.getCollection("mo_log_201208");
		String mapfun = "function() {"
				+ "emit({Spcode:this.Spcode, Spname:this.Spname, "
				+ "Consignid:this.Consignid, Consname:this.Consname, "
				+ "Region:this.Region, Regionname:this.Regionname, "
				+ "Serviceid:this.Serviceid, "
				+ "Servicename:this.Servicename, "
				+ "Srctermid:this.Srctermid}, {count:1});" + "}";
		String reducefun = "function(key, value) {" + "var ret = {count:0};"
				+ "ret.count++;" + "return ret;" + "}";
		DBObject query = new BasicDBObject("Logtime", new BasicDBObject("$gte",
				"20120823").append("$lte", "20120824"));
		MapReduceOutput mro = dbc.mapReduce(mapfun, reducefun,
				"tmp_mo_spcode_consignid_region_serviceid_201208_1", query);
	}

	/**
	 * 比上一个方法多了两个参数
	 * @throws Exception
	 */
	public void mapReduce3() throws Exception{
		MongoClient mongo = new MongoClient("host", 27017);
		DB db = mongo.getDB("dbName");
	    DBCollection dbc = db.getCollection("mo_log_201208");
	    String mapfun = "function() {" +
	        "emit({Spcode:this.Spcode, Spname:this.Spname, " +
	                "Consignid:this.Consignid, Consname:this.Consname, " +
	                "Region:this.Region, Regionname:this.Regionname, " +
	                "Serviceid:this.Serviceid, " +   
	                "Servicename:this.Servicename, " +
	                "Srctermid:this.Srctermid}, {count:1});" +
	                "}";
	    String reducefun = "function(key, value) {" +
	        "var ret = {count:0};" +
	        "ret.count++;" +
	        "return ret;" +
	        "}";
	    String finalizefun = "function(key, value){" +
	        "db.tmp_mo_spcode_consignid_region_serviceid_201208.insert({\"_id\":key, \"value\":value});" +
	        "return value;" +
	        "}";
	    DBObject query = new BasicDBObject("Logtime", 
	                        new BasicDBObject("$gte", "20120823").append("$lte", "20120824"));
	    DBObject command = new BasicDBObject();
	    command.put("mapreduce", "mo_log_201208");
	    command.put("query", query);
	    command.put("map", mapfun);
	    command.put("reduce", reducefun);
	    command.put("finalize", finalizefun);
	    command.put("out", "tmp_mo_spcode_consignid_region_serviceid_201208_1");
	    command.put("verbose", true);
	    //在dbcollection上执行mapreduce
	    MapReduceOutput mro = dbc.mapReduce(command);
	    //在db上使用command执行mapreduce
	    CommandResult cr = db.command(command);
	}
}