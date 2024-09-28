package com.personal.jobhive.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import com.personal.jobhive.entities.Job;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            // sign with google
            System.out.println("Getting email from google");
            username = oauth2User.getAttribute("email").toString();
            return username;

        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }

    public static List<String> categories = Arrays.asList(
         "Applied", "To Be Applied","Shortlisted", "Not Shortlisted", 
        "Waiting", "OA Scheduled", "OA Given", "Interview Scheduled", 
        "Interview Given", "Selected", "Rejected", "Offer Received", 
        "Offer Accepted", "Offer Rejected"
    );

    private static final Set<String> successStatus = new HashSet<>(Arrays.asList(
        "Selected", "Offer Received", "Offer Accepted", "Offer Rejected"
    ));

    public static int[] getChartData(List<Job> jobs) {
        // Create a map to hold the count of jobs for each status
        Map<String, Integer> statusCountMap = new HashMap<>();
        
        // Initialize the map with 0 counts for each category
        for (String category : categories) {
            statusCountMap.put(category, 0);
        }

        // Count occurrences of each status in the job list
        for (Job job : jobs) {
            String status = job.getCurrentStatus();
            if (statusCountMap.containsKey(status)) {
                statusCountMap.put(status, statusCountMap.get(status) + 1);
            }
        }

        // Convert the counts from the map into an array
        int[] result = new int[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            result[i] = statusCountMap.get(categories.get(i));
        }

        return result;
    }

    public static double calculateSuccessPercentage(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return 0.0; // Return 0 if there are no jobs to evaluate
        }

        // Count how many jobs have a "successful" status
        long successfulJobs = jobs.stream()
                .filter(job -> successStatus.contains(job.getCurrentStatus()))
                .count();

        // Calculate percentage
        return ((double) successfulJobs / jobs.size()) * 100;
    }
    public static String getLinkForEmailVerificatiton(String emailToken) {
        String link = "http://localhost:8080/auth/verify-email?token=" + emailToken;
        return link;
    }
}