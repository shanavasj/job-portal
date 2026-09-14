package com.shanavas.jobportal.controller;

import com.shanavas.jobportal.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Plain @Controller (NOT @RestController) - methods return a JSP view name (a String),
// which the view resolver in application.properties turns into
// /WEB-INF/jsp/<viewName>.jsp, instead of returning JSON.
@Controller
public class HomeController {

    private final JobPostingService jobPostingService;

    @Autowired
    public HomeController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    // GET / -> shows the home page listing all jobs
    @GetMapping("/")
    public String home(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("jobs", jobPostingService.searchByTitle(keyword));
        } else {
            model.addAttribute("jobs", jobPostingService.getAllJobs());
        }
        model.addAttribute("keyword", keyword);
        return "index"; // resolves to /WEB-INF/jsp/index.jsp
    }

    // GET /jobs/{id} -> job details page
    @GetMapping("/jobs/{id}")
    public String jobDetails(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        model.addAttribute("job", jobPostingService.getJobById(id));
        return "job-details";
    }

    // GET /post-job -> form to post a new job
    @GetMapping("/post-job")
    public String postJobForm() {
        return "post-job";
    }
}
