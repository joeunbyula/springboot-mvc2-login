package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {
    //init과 destory는 default로 선언되어 굳이 작성안해도된다.

    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);
            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행==>{}", requestURI);
                HttpSession session = httpRequest.getSession();
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) ==  null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                }
            }
            chain.doFilter(request,response);
        } catch (Exception e) {
            log.error("error==>{}",e.getLocalizedMessage());
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}",requestURI);
        }
    }

    /**
     * 화이트리스트의 경우 인증 체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
