package com.zzy.base.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zzy.base.common.context.SystemContext;

@Service
public class LoginInterceptor implements HandlerInterceptor{

    public void afterCompletion(HttpServletRequest arg0,
	    HttpServletResponse arg1, Object arg2, Exception arg3)
	    throws Exception {
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
	    Object arg2, ModelAndView arg3) throws Exception {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	    Object arg2) throws Exception {
    	if(!request.getRequestURI().contains("login") && request.getSession().getAttribute("user")==null){
    		response.sendRedirect(request.getContextPath()+"/login.jsp");
    		//request.getRequestDispatcher("/login.jsp").forward(request, response);
        	return false;
    	}else{
    		return true;
    	}
    	
    }
}
