package com.branchexercise.client;

// simple custom exception to aid in handling 400/500 errors
public class GitHubException extends RuntimeException {
    public GitHubException() {super();}
    public GitHubException(String message) {
        super(message);
    }
}
