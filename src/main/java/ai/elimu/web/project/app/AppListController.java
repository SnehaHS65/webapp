package ai.elimu.web.project.app;

import ai.elimu.dao.project.ProjectDao;
import ai.elimu.model.project.Project;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project/{projectId}/app-category/{appCategoryId}/app-group/{appGroupId}/app")
public class AppListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String handlRequest(
            Model model,
            @PathVariable Long projectId,
            HttpSession session
    ) {
    	logger.info("handleRequest");
        
        logger.info("projectId: " + projectId);
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        return "project/app/list";
    }
}
