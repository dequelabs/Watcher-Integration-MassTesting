package com.deque.util;

/**
 * Utility class for Git-related operations.
 */
public class GitUtils {

    /**
     * Generates a random branch name based on the current method name and a random number.
     *
     * @param methodName The name of the method for which the branch is being created.
     * @return The generated branch name.
     */
    public static String generateBranchName(String methodName) {
        return "test-branch-" + (int) (Math.random() * 10000) + "-" + methodName;
    }

    /**
     * Executes a Git command to create and switch to a new branch.
     *
     * @param branchName The name of the branch to create and switch to.
     */
    public static void checkoutNewBranch(String branchName) {
        try {
            // Execute the Git checkout command
            Process process = Runtime.getRuntime().exec(new String[]{"git", "checkout", "-b", branchName});
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create and switch to branch: " + branchName, e);
        }
    }
}