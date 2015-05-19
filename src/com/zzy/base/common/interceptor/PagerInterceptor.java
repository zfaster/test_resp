package com.zzy.base.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zzy.base.common.context.SystemContext;

@Service
public class PagerInterceptor implements HandlerInterceptor{

	private final static int DEFAULTPAGESIZE = 10;
    public void afterCompletion(HttpServletRequest arg0,
	    HttpServletResponse arg1, Object arg2, Exception arg3)
	    throws Exception {
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
	    Object arg2, ModelAndView arg3) throws Exception {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	    Object arg2) throws Exception {
    	HttpSession session = request.getSession();
    	String uri = request.getRequestURI();
    	if(uri.contains("/static/")){
    		return true;
    	}
    	String offset = request.getParameter("pager.offset");
		
		if(offset != null){
			int _offset = 0;
			try{
				_offset = Integer.parseInt(offset);
			}catch(Exception e){
				_offset = 0;
			}
			if(_offset<0) _offset = 0;
			SystemContext.setOffset(_offset);
		}else{
			SystemContext.setOffset(0);
			SystemContext.setPageSize(DEFAULTPAGESIZE);
			return true;
		}
		int _pageSize = DEFAULTPAGESIZE;
		String pageSize = request.getParameter("pageSize");
		if(pageSize!=null){
			
			_pageSize = DEFAULTPAGESIZE;
			try{
				_pageSize = Integer.parseInt(pageSize);
				if(_pageSize < 0){
					_pageSize = DEFAULTPAGESIZE;
				}
			}catch(Exception e){
				_pageSize = DEFAULTPAGESIZE;
			}
			session.setAttribute("pageSize", _pageSize);
		}
		
		SystemContext.setPageSize(_pageSize);
    	return true;
    }
}
