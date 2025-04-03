package seedu.address.util;

/**
 * Utility class for formatting feedback text.
 */
public class FeedbackFormatter {
    /**
     * Formats the feedback text for better readability.
     */
    public static String formatFeedback(String feedback) {
        String[] lines = feedback.split("\\n");
        StringBuilder formatted = new StringBuilder();

        for (String line : lines) {
            if (line.contains(":")) {
                String prefix = line.contains(":") ? line.substring(0, line.indexOf(":") + 1) : "";
                formatted.append(prefix).append("\n");

                String paramsSection = line.substring(prefix.isEmpty() ? 0 : prefix.indexOf(":") + 1).trim();
                String[] params = paramsSection.split(";");

                for (String param : params) {
                    param = param.trim();
                    if (!param.isEmpty()) {
                        formatted.append("• ").append(param).append("\n");
                    }
                }
            } else {
                if (!line.trim().isEmpty()) {
                    formatted.append(line.trim()).append("\n");
                }
            }
        }

        return formatted.toString().trim();
    }
}
