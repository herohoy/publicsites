/**
 * 
 */
package com.op.clazz;

/**
 * @author Administrator 中文
 *
 */
public class NewClass {
	private String id;
	private int count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
	private static String teacherRole = 
			"{[空间用户角色].[Role Name].[schoolmaster],[空间用户角色].[Role Name].[director]"
			+ ",[空间用户角色].[Role Name].[2000000037000000023],[空间用户角色].[Role Name].[teacher]"
			+ ",[空间用户角色].[Role Name].[schCheckResource],[空间用户角色].[Role Name].[subjectGroup]"
			+ ",[空间用户角色].[Role Name].[gradeGroup],[空间用户角色].[Role Name].[schoolMng]"
			+ ",[空间用户角色].[Role Name].[schNoticeMng]}";

	  private static String instructorRole = 
			  "{[空间用户角色].[Role Id].[2000000037000000026]"
			  + ",[空间用户角色].[Role Id].[2000000037000000027]"
			  + ",[空间用户角色].[Role Id].[2000000037000000028]"
			  + ",[空间用户角色].[Role Id].[2300000001000000006]}";

	public static void main(String[] args) {
		int type=3;
		String cityId = "140701";
		int start = 0;
		int size = 20;
		
		StringBuffer sbf = new StringBuffer();
		sbf.append("WITH ");
	    sbf.append("Member [教师基数] AS SUM(" + teacherRole + ",[Measures].[Space Open Count 计数])");
	    sbf.append("Member [教研员基数] AS SUM(" + instructorRole + ",[Measures].[Space Open Count 计数])");
	    sbf.append("MEMBER [教师开通数] AS (SUM(" + teacherRole + ",([Measures].[Space Open Count 计数],[空间开设].[Open Name].[已激活空间])))");
	    sbf.append("MEMBER [教师开通率] AS (SUM(" + teacherRole + ",([Measures].[Space Open Count 计数],[空间开设].[Open Name].[已激活空间]))/[教师基数])*100");
	    sbf.append("Member [教研员开通率] AS (SUM(" + instructorRole + ",([Measures].[Space Open Count 计数],[空间开设].[Open Name].[已激活空间]))/[教研员基数])*100");
	    sbf.append("MEMBER [教师活跃数] AS (SUM(" + teacherRole + ",([Measures].[Space Open Count 计数],[空间活跃].[Active Name].[活跃用户])))");
	    sbf.append("MEMBER [教师活跃率] AS (SUM(" + teacherRole + ",([Measures].[Space Open Count 计数],[空间活跃].[Active Name].[活跃用户]))/[教师基数])*100");
	    sbf.append("Member [教研员活跃率] AS (SUM(" + instructorRole + ",([Measures].[Space Open Count 计数],[空间活跃].[Active Name].[活跃用户]))/[教研员基数])*100");
	    sbf.append(" SELECT {[教师开通数],[教师开通率],[教师活跃数],[教师活跃率],[教研员开通率],[教研员活跃率],[教师基数]} ON 0,");

	    switch (type)
	    {
	    case 1:
	      sbf.append("subset(Order([空间区域].[City Id 1].children*[空间区域].[City Name].children,[教师开通率],bdesc)," + start + "," + size + ") ON 1");
	      sbf.append(" FROM [空间开设]  where (  [空间区域].[Province Id].[" + cityId + "]  )"); break;
	    case 2:
	      sbf.append("subset(Order([空间区域].[County Id].children*[空间区域].[County Name].children,[教师开通率],bdesc)," + start + "," + size + ") ON 1");
	      sbf.append(" FROM [空间开设]  where (  [空间区域].[City Id 1].[" + cityId + "]  )"); break;
	    case 3:
	      sbf.append("subset(Order([学校].[School Id].children*[学校].[School Name].children,[教师开通率],bdesc)," + start + "," + size + ") ON 1");
	      sbf.append(" FROM [空间开设]  where (  [学校].[County Id].[" + cityId + "]  )"); break;
//	    case 4:
//	      return userLoginGrird(page, size, cityId);
	    default:
	      sbf.append("subset(Order([空间区域].[County Id].children*[空间区域].[County Name].children,[教师开通率],bdesc)," + start + "," + size + ") ON 1");
	      sbf.append(" FROM [空间开设]  where (  [空间区域].[City Id 1].[" + cityId + "]  )");
	    }
	    
	    System.out.println(sbf.toString());
	}
}
