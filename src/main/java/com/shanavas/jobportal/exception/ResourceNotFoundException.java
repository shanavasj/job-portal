package com.shanavas.jobportal.exception;

// Thrown when a JobPosting/Applicant/Employer/Application lookup by ID fails.
// Caught by a @ControllerAdvice (GlobalExceptionHandler) to return a clean 404 JSON response
// instead of a raw stack trace - this is what "graceful error handling" means in interviews.
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
