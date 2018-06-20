package com.dong.utils;

/**
 * System帮助类
 * @version 1.0 2018/06/20
 * @author dongliyang
 */
public class SystemUtil {
	
	/** 获取Java运行时环境版本 */
	public static String getJavaVerson() {
		return System.getProperty(SysProp.JAVA_VERSON);
	}

	/** 获取Java安装目录 */
	public static String getJavaHome() {
		return System.getProperty(SysProp.JAVA_HOME);
	}

	/** 获取操作系统的名称 */
	public static String getOsName() {
		return System.getProperty(SysProp.OS_NAME);
	}

	/** 获取操作系统的架构 */
	public static String getOsArch() {
		return System.getProperty(SysProp.OS_ARCH);
	}

	/** 获取操作系统的版本 */
	public static String getOsVersion() {
		return System.getProperty(SysProp.OS_VERSION);
	}

	/** 获取文件分隔符 */
	public static String getFileSeparator() {
		return System.getProperty(SysProp.FILE_SEPARATOR);
	}

	/** 获取路径分隔符 */
	public static String getPathSeparator() {
		return System.getProperty(SysProp.PATH_SEPARATOR);
	}

	/** 获取行分隔符 */
	public static String getLineSeparator() {
		return System.getProperty(SysProp.LINE_SEPARATOR);
	}

	/** 获取用户的账户名称 */
	public static String getUserName() {
		return System.getProperty(SysProp.USER_NAME);
	}

	/** 获取用户的主目录 */
	public static String getUserHome() {
		return System.getProperty(SysProp.USER_HOME);
	}

	/** 获取用户的当前工作目录 */
	public static String getUserDir() {
		return System.getProperty(SysProp.USER_DIR);
	}
	
	/** 系统属性 */
	interface SysProp {
		/** Java运行时环境版本 */
		public static final String JAVA_VERSON = "java.version";
		/** Java安装目录 */
		public static final String JAVA_HOME = "java.home";
		/** 操作系统的名称 */
		public static final String OS_NAME = "os.name";
		/** 操作系统的架构 */
		public static final String OS_ARCH = "os.arch";
		/** 操作系统的版本 */
		public static final String OS_VERSION = "os.version";
		/** 文件分隔符 */
		public static final String FILE_SEPARATOR = "file.separator";
		/** 路径分隔符 */
		public static final String PATH_SEPARATOR = "path.separator";
		/** 行分隔符 */
		public static final String LINE_SEPARATOR = "line.separator";
		/** 用户的账户名称 */
		public static final String USER_NAME = "user.name";
		/** 用户的主目录 */
		public static final String USER_HOME = "user.home";
		/** 用户的当前工作目录 */
		public static final String USER_DIR = "user.dir";
	}
}
