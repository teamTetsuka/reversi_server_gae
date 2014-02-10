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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

@SuppressWarnings("serial")
public class StartServlet extends HttpServlet {
	
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Query q = new Query("r");
		q.setFilter(FilterOperator.EQUAL.of("t", -1));
		PreparedQuery pq = ds.prepare(q);
		Entity e = null;
		String ret;
		for(Entity _e:pq.asIterable()){
			e = _e;
			break;
		}
		if (e == null){
			e = new Entity("r");
			e.setProperty("t", -1);
			ds.put(e);
			ret = "{\"id\":\"" + e.getKey().getId() + "\"" + ",\"p\":1}";
		}else{
			e.setProperty("t", 0);
			ds.put(e);
			ret = "{\"id\":\"" + e.getKey().getId() + "\"" + ",\"p\":2}";
		}
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.write(ret);
	}

}
