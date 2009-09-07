package org.ertuo.douche.biz.nineteen.test;


import org.hsqldb.Server;
import org.hsqldb.ServerConfiguration;
import org.hsqldb.ServerConstants;
import org.hsqldb.lib.FileUtil;
import org.hsqldb.persist.HsqlProperties;
 
public class HSQLServer extends Server {  
   public static void start(String[] args) {  
       String propsPath = FileUtil.getDefaultInstance().canonicalOrAbsolutePath("server");  
       HsqlProperties fileProps = ServerConfiguration  
               .getPropertiesFromFile(propsPath);  
       HsqlProperties props = fileProps == null ? new HsqlProperties()  
               : fileProps;  
       HsqlProperties stringProps = HsqlProperties.argArrayToProps(args,  
               ServerConstants.SC_KEY_PREFIX);  
 
       if (stringProps != null) {  
           if (stringProps.getErrorKeys().length != 0) {  
               printHelp("server.help");  
               return;  
           }  
           props.addProperties(stringProps);  
       }  
       ServerConfiguration.translateDefaultDatabaseProperty(props);  
 
       ServerConfiguration.translateDefaultNoSystemExitProperty(props);  
 
       Server server = new Server();  
 
       server.setProperties(props);  
 
       server.start();  
   } 
   /**
    * ���� Hsqldb ����ķ�����
    *
    * @param dbPath
    *            ���ݿ�·��
    * @param dbName
    *            ���ݿ�����
    * @param port
    *            �˿ں�
    */
   private static void startServer(String dbPath, String dbName, int port) {
    Server server = new Server();// ������hsqldb.jar������డ��
    server.setDatabaseName(0, dbName);
    server.setDatabasePath(0, dbPath + dbName);
    if (port != -1) {
     server.setPort(port);
    }
    server.setSilent(true);
    server.start();
    System.out.println("HSQLDB started...");
    
   }
   public static void main(String[] arg){
	   
//	   HSQLServer.startServer("F:/douche/db/myhsql", "douche", 9999);
	   
	    String[] args=new String[]{"-database.0","file:mydb","-dbname.0","douche"};  
		HSQLServer.start(args);
		System.out.println("����HSQL���");
   }
}
