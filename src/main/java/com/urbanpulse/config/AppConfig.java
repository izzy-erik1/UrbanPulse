package com.urbanpulse.config;

import com.urbanpulse.repository.*;
import com.urbanpulse.repository.impl.hibernate.*;
import com.urbanpulse.service.*;

public class AppConfig {

    private static AppConfig instance;

    private final UserRepository userRepository;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    private final MunicipalityRepository municipalityRepository;

    private final AuthService authService;
    private final UserService userService;
    private final MunicipalityService municipalityService;
    private final IssueService issueService;
    private final CommentService commentService;
    private final NotificationService notificationService;

    private AppConfig() {
        // Repositories
        this.userRepository = new HibernateUserRepository();
        this.issueRepository = new HibernateIssueRepository();
        this.commentRepository = new HibernateCommentRepository();
        this.notificationRepository = new HibernateNotificationRepository();
        this.municipalityRepository = new HibernateMunicipalityRepository();

        // Services
        this.authService = new AuthService(userRepository);
        this.userService = new UserService(userRepository);
        this.municipalityService = new MunicipalityService(municipalityRepository);
        this.issueService = new IssueService(issueRepository);
        this.commentService = new CommentService(commentRepository);
        this.notificationService = new NotificationService(notificationRepository);
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public AuthService getAuthService() { return authService; }
    public UserService getUserService() { return userService; }
    public MunicipalityService getMunicipalityService() { return municipalityService; }
    public IssueService getIssueService() { return issueService; }
    public CommentService getCommentService() { return commentService; }
    public NotificationService getNotificationService() { return notificationService; }
}