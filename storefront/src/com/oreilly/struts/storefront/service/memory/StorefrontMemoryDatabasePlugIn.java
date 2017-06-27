package com.oreilly.struts.storefront.service.memory;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.webapp.example.Constants;
import org.apache.struts.webapp.example.memory.MemoryDatabasePlugIn;

public final class StorefrontMemoryDatabasePlugIn extends MemoryDatabasePlugIn {
  private ModuleConfig config = null;
  private StorefrontMemoryDatabase database = null;
  private Log log = LogFactory.getLog(this.getClass());
  private ActionServlet servlet = null;
  private String pathname = "/WEB-INF/database.xml";

    public String getPathname() {
        return (this.pathname);
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    /**
     * Gracefully shut down this database, releasing any resources
     * that were allocated at initialization.
     */
    public void destroy() {

        log.info("Finalizing memory database plug in");

        if (database != null) {
            try {
                database.close();
            } catch (Exception e) {
                log.error("Closing memory database", e);
            }
        }

	      servlet.getServletContext().removeAttribute(Constants.DATABASE_KEY);
        database = null;
        servlet = null;
        database = null;
        config = null;
    }


    /**
     * Initialize and load our initial database from persistent storage.
     *
     * @param servlet The ActionServlet for this web application
     * @param config The ApplicationConfig for our owning module
     *
     * @exception ServletException if we cannot configure ourselves correctly
     */
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        log.info("Initializing memory database plug in from '" +
                 pathname + "'");

        // Remember our associated configuration and servlet
        this.config = config;
        this.servlet = servlet;

        // Construct a new database and make it available
        database = new StorefrontMemoryDatabase();
        try {
            String path = calculatePath();
            if (log.isDebugEnabled()) {
                log.debug(" Loading database from '" + path + "'");
            }
            database.setPathname(path);
            database.open();
        } catch (Exception e) {
            log.error("Opening memory database", e);
            throw new ServletException("Cannot load database from '" +
                                       pathname + "'", e);
        }

        // Make the initialized database available
        servlet.getServletContext().setAttribute(Constants.DATABASE_KEY,
                                                 database);

    }

    /**
     * Calculate and return an absolute pathname to the XML file to contain
     * our persistent storage information.
     *
     * @exception Exception if an input/output error occurs
     */
    private String calculatePath() throws Exception {

        // Can we access the database via file I/O?
        String path = servlet.getServletContext().getRealPath(pathname);
        if (path != null) {
            return (path);
        }

        // Does a copy of this file already exist in our temporary directory
        File dir = (File)
            servlet.getServletContext().getAttribute
            ("javax.servlet.context.tempdir");
        File file = new File(dir, "struts-example-database.xml");
        if (file.exists()) {
            return (file.getAbsolutePath());
        }

        // Copy the static resource to a temporary file and return its path
        InputStream is =
            servlet.getServletContext().getResourceAsStream(pathname);
        BufferedInputStream bis = new BufferedInputStream(is, 1024);
        FileOutputStream os =
            new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        byte buffer[] = new byte[1024];
        while (true) {
            int n = bis.read(buffer);
            if (n <= 0) {
                break;
            }
            bos.write(buffer, 0, n);
        }
        bos.close();
        bis.close();
        return (file.getAbsolutePath());

    }
}
