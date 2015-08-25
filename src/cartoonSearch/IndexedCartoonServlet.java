package cartoonSearch;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")

/*
 * The IndexedCartoonServlet class controls the server reaction to the requests from browser.
 * When user access the web site for the first time and no search parameter is passed in,
 * the index.jsp view will be opened in the user's brower. 
 * If the user choose the category and click submit, then the servlet will return the 
 * result.jsp view with a cartoon of the corresponding  which is scraped from thisisindex.com.
 * It will also look for the device that the user employed for browsing the website,
 * and return the cartoon which is fit for the device.
 * 
 */
public class IndexedCartoonServlet extends HttpServlet {

    IndexedCartoonModel ipm = null;  // The "business model" for this app

    @Override
    public void init() {
        ipm = new IndexedCartoonModel();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get the search parameter if it exists
        String search = request.getParameter("category");

        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.contains("Android")) || (ua.contains("iPhone")))) {
            mobile = true;
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;
        /*
         * Check if the search parameter is present.
         * If not, then give the user instructions and prompt for choosing a category.
         * If the category is chosen, then do the search and return the result.
         */
        if (search != null) {
            // use model to do the search and choose the result view
            ipm.doIndexedSearch(search);
            request.setAttribute("pictureURL",
                    ipm.indexedCartoonSize(
                            (mobile) ? "mobile" : "desktop"));
            request.setAttribute("pictureTag", ipm.getPictureTag());
            nextView = "result.jsp";
        } else {
            // no search parameter so choose the prompt view
            nextView = "index.jsp";
        }
        
        
        // Transfer control over the the correct "view"
        
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}

