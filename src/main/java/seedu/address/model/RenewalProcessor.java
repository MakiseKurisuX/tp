package seedu.address.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;

/**
 * Processes and manages policy renewal data.
 */
public class RenewalProcessor {
    /**
     * Represents a processed renewal entry.
     */
    public static class RenewalEntry {
        private final String client;
        private final String policy;
        private final LocalDate renewalDate;
        private final long daysLeft;
        private final String type;
        private final String contact;
        /**
         * Constructs a renewal entry from a person.
         *
         * @param person the person to create the renewal entry from
         */
        public RenewalEntry(Person person) {
            this.client = person.getName().toString();
            this.policy = person.getPolicy().policyNumber;
            this.renewalDate = person.getPolicy().renewalDate.value;
            this.daysLeft = person.getPolicy().getDaysUntilRenewal();
            this.type = person.getPolicy().getType().toString();
            this.contact = person.getPhone().toString();
        }

        public String getClient() {
            return client;
        }

        public String getPolicy() {
            return policy;
        }

        public LocalDate getRenewalDate() {
            return renewalDate;
        }

        public long getDaysLeft() {
            return daysLeft;
        }

        public String getType() {
            return type;
        }

        public String getContact() {
            return contact;
        }
    }

    /**
     * Processes the list of persons and returns table data for renewals.
     *
     * @param persons List of persons to process
     * @return Table data containing processed renewal entries
     */
    public static RenewalTableData processRenewals(List<Person> persons) {
        List<RenewalEntry> entries = persons.stream()
                .map(RenewalEntry::new)
                .sorted(Comparator.comparingLong(RenewalEntry::getDaysLeft))
                .collect(Collectors.toList());
        return new RenewalTableData(entries);
    }
}
