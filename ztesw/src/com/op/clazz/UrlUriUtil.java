package com.op.clazz;

/**
 * 域名（ip）组装工具
 * @author Administrator
 *
 */
public class UrlUriUtil {
	
	/**
	 * 将url中的端口去掉
	 * @param origin 原url（如http://localhost:8080/app）
	 * @return 去掉端口的url（如http://localhost/app）
	 */
	public static String removePort(String origin){
		return modifyProtocolAndPort(origin,null,null);
	}
	
	/**
	 * 给url添加或替换端口号
	 * @param origin 原url（如http://localhost:8080/app）
	 * @param port 要替换成的端口号，如10000；传负数表示去掉端口号
	 * @return 已替换端口的url（如http://localhost:10000/app）
	 */
	public static String adOrMdPort(String origin,int port){
		return modifyProtocolAndPort(origin,null,port);
	}
	
	/**
	 * 替换url协议的方法（其他不变）
	 * @param origin 原url（如http://localhost:8080/app）
	 * @param protocol 要替换的协议，如ftp；传null或无效空字符串表示不替换协议
	 * @return 替换协议后的结果url（如ftp://localhost:8080/app）
	 */
	public static String modifyProtocol(String origin,String protocol){
		//直接使用统一方法（如下）会去掉端口
		/*
		return modifyProtocolAndPort(origin,protocol,null);
 		*/
		if(origin==null){
			return null;
		}
		String result = null;
		int index = origin.indexOf("://");
		if(index != -1){
			index = index + "://".length();
		}else{
			index = 0;
		}
		if(protocol!=null){
			protocol = protocol.replaceAll(" ", "");
			if(protocol.length()>0){
				if(protocol.endsWith("://")){
					origin = protocol + origin.substring(index);
					index = protocol.length();
				}else{
					origin = protocol + "://" + origin.substring(index);
					index = protocol.length() + "://".length();
				}
			}
		}
		result = origin;
		return result;
	}
	
	/**
	 * 替换url协议和端口核心方法
	 * @param origin 原url（如http://localhost:8080/app）
	 * @param protocol 要替换的协议，如ftp；传null或无效空字符串表示不替换协议
	 * @param port 要替换成的端口号，如10000；传null或负数表示去掉端口号
	 * @return 已替换协议和端口的url（如http://localhost:10000/app）
	 */
	public static String modifyProtocolAndPort(String origin,String protocol,Integer port){
		return modifyProtocolAndPort(origin,protocol,port,null,null,null);
	}
	/**
	 * 替换url协议和端口核心方法（可适用于数据库连接的写法）
	 * @param origin 原url（如http://localhost:8080/app）
	 * @param protocol 要替换的协议，如ftp；传null或无效空字符串表示不替换协议
	 * @param port 要替换成的端口号，如10000；传null或负数表示去掉端口号
	 * @param isDbUri 是否数据库连接 (null不变,false去掉db部分,true将用户名密码替换为入参)
	 * @param dbName 数据库用户名(仅isDbUri为true时可用)
	 * @param dbPwd 数据库密码(仅isDbUri为true时可用)
	 * @return
	 */
	public static String modifyProtocolAndPort(String origin,String protocol,Integer port
			,Boolean isDbUri,String dbName,String dbPwd){
		if(origin==null){
			return null;
		}
		String result = null;
		StringBuffer str = new StringBuffer();
		int index = origin.indexOf("://");
		if(index != -1){
			index = index + "://".length();
		}else{
			index = 0;
		}
		if(protocol!=null){
			protocol = protocol.replaceAll(" ", "");
			if(protocol.length()>0){
				if(protocol.endsWith("://")){
					origin = protocol + origin.substring(index);
					index = protocol.length();
				}else{
					origin = protocol + "://" + origin.substring(index);
					index = protocol.length() + "://".length();
				}
			}
		}
		result = origin.substring(index);
		int endcut = index + result.length();
		if(result.indexOf("/")!=-1){
			endcut = index + result.indexOf("/");
		}
		int firstSpl = result.indexOf("/"); //url正文第一个斜杠
		if(firstSpl == -1){
			firstSpl = result.length();
		}
		int portMh = -1; //端口前的冒号
		int atIndex = -1;
		if(firstSpl != -1){
			portMh = result.substring(0, firstSpl).lastIndexOf(":");
			atIndex = result.substring(0, firstSpl).lastIndexOf("@");
		}
		int begincut = endcut;
		if(firstSpl!=-1 &&
				portMh!=-1
				&& portMh>atIndex){
			begincut = index + portMh;
		}
		if(isDbUri == null){
			str.append(origin.substring(0, begincut));
		}else if(!isDbUri || dbName==null || dbPwd==null
				|| "".equals(dbName.trim())
				|| "".equals(dbPwd.trim())){
			str.append(origin.substring(0, index));
			str.append(origin.substring(index+atIndex+1, begincut));
		}else{
			str.append(origin.substring(0, index));
			str.append(dbName);
			str.append(":");
			str.append(dbPwd);
			str.append("@");
			str.append(origin.substring(index+atIndex+1, begincut));
		}
		if(port!=null && port>=0){
			str.append(":");
			str.append(port);
		}
		str.append(origin.substring(endcut, origin.length()));
		result = str.toString();
		return result;
	}
	

	public static void main(String[] args) {
		// jdbc:mysql://localhost:3306/user_center?useUnicode=true&characterEncoding=utf-8
		// jdbc:oracle:thin:@localhost:1521:ora92
		// jdbc:db2://localhost:50000/sample
		// jdbc:sqlserver://localhost:1433;DatabaseName=sse
		
//		System.out.println(DomainNameUtil.removePort("http://localhost:8080/app"));
//		System.out.println(DomainNameUtil.removePort("http://localhost:8080"));
//		System.out.println(DomainNameUtil.removePort("localhost:8080/app"));
//		System.out.println(DomainNameUtil.removePort("localhost:8080"));
//		System.out.println(DomainNameUtil.removePort("http://localhost/app"));
//		System.out.println(DomainNameUtil.removePort("http://localhost"));
//		System.out.println(DomainNameUtil.removePort("localhost/app"));
//		System.out.println(DomainNameUtil.removePort("localhost"));
//
//		System.out.println(DomainNameUtil.adOrMdPort("http://localhost:8080/app",10000));
//		System.out.println(DomainNameUtil.adOrMdPort("http://localhost:8080",10000));
//		System.out.println(DomainNameUtil.adOrMdPort("localhost:8080/app",10000));
//		System.out.println(DomainNameUtil.adOrMdPort("localhost:8080",10000));
//		System.out.println(DomainNameUtil.adOrMdPort("http://localhost/app",10000));
//		System.out.println(DomainNameUtil.adOrMdPort("http://localhost",10000));
//		System.out.println(DomainNameUtil.adOrMdPort("localhost/app",10000));
//		System.out.println(DomainNameUtil.adOrMdPort("localhost",0));
//		
		System.out.println(UrlUriUtil.adOrMdPort("http://root:pwd@localhost:8080/app",-1));
		System.out.println(UrlUriUtil.adOrMdPort("http://root:pwd@localhost:8080",-1));
		System.out.println(UrlUriUtil
				.modifyProtocolAndPort(
						"http://root:pwd@localhost:8080/app"
						,null
						,3306
						)
				);
		System.out.println(UrlUriUtil
				.modifyProtocolAndPort(
						"http://root:pwd@localhost:8080/app"
						,"mysql"
						,3306
						,true
						,null
						,"mypwd"
						)
				);
		System.out.println(UrlUriUtil
				.modifyProtocolAndPort(
						"http://root:pwd@localhost:8080/app"
						,"mysql"
						,3306
						,true
						,"myroot"
						,"mypwd"
						)
				);
		

//		System.out.println(UrlUriUtil.modifyProtocol("http://localhost/app","ftp://"));
//		System.out.println(UrlUriUtil.modifyProtocol("localhost:8080/app","ftp"));
		
//		System.out.println("昆明市育才小学".replaceAll("市", "").replaceAll("","%"));
//		System.out.println("昆明市育才小学".replaceAll("市", "*").replaceAll("学","*"));
//		System.out.println("昆明******才******小学".replaceAll("[\\*]{2,}", "\\*"));
//		System.out.println("澂江县阳宗镇净莲寺小学".matches("(.*)净莲寺小(.*)"));
//		System.out.println("昆明**经开***一中*".matches("(.*)[\\*]{2,}(.*)"));
//		System.out.println("昆明经开区第一中学".substring(0,"昆明经开区第一中学".length()));
	}

}
