package isjuku.reversi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class PutServlet extends HttpServlet {
	
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String ret = "";
		try{
			Key k = KeyFactory.createKey("r", Long.parseLong(req.getParameter("id")));
			Entity e = ds.get(k);
			long t = (Long)e.getProperty("t");
			e.setProperty("t", t + 1);
			e.setProperty("p", req.getParameter("p"));
			e.setProperty("r", req.getParameter("r"));
			e.setProperty("c", req.getParameter("c"));
			ds.put(e);
			ret = "{}";
		}catch(Exception e){
			ret = "{\"error\":true}";
		}
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.write(ret);
	}

}
