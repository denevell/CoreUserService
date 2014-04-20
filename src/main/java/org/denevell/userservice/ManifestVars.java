package org.denevell.userservice;

import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/** 
 * Reads the war's manifest from /META-INF/MANIFEST.MF on application startup.
 * 
 * Must be included as the first <listener></listener> in the web.xml file.
 */
public class ManifestVars implements ServletContextListener {

	private static boolean sInProduction;
	private static Attributes sMainManifestAttributes;

	/**
	 * Read the manifest from /META-INF/MANIFEST.MF
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
			Logger.getLogger(getClass()).info(inputStream);
			Manifest manifest = new Manifest(inputStream);
			sMainManifestAttributes = manifest.getMainAttributes();
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Coulnd't read manifest entries. Bailing.", e);
			throw new RuntimeException(e);
		}	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sMainManifestAttributes = null;
	}

	/**
	 * Generic querying of the manifest.
	 * @return The result, as run through String.trim()
	 */
	public static String getValue(String name) {
		return sMainManifestAttributes.getValue(name).trim();
	}

}
