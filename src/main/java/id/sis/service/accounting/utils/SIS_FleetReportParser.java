package id.sis.service.accounting.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SIS_FleetReportParser {

    private static final SimpleDateFormat DATE_TIME_FORMAT =
            new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    private static final Pattern TRANSACTION = Pattern.compile(
            "^\\s*(\\d+)\\s+" +              // no urut
            "(\\d+)\\s+" +                   // no kartu
            "(\\d{2}/\\d{2}/\\d{2})\\s+" +  // tanggal
            "Rp\\s+" +
            "([\\d,]+\\.\\d{2})\\s+" +      // nominal
            "(.+?)\\s+" +                   // terminal
            "(\\d+)\\s*$"                    // kode
    );

    private static final Pattern TIME_LINE = Pattern.compile(
            "^\\s*(\\d{2}:\\d{2}:\\d{2})\\s+(.*)$"
    );

    public static List<FleetTransaction> parse(Path file)
            throws IOException, ParseException {

        List<FleetTransaction> result = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String line;

            while ((line = reader.readLine()) != null) {

                Matcher transactionMatcher =
                        TRANSACTION.matcher(line);

                if (!transactionMatcher.matches()) {
                    continue;
                }

                int noUrut = Integer.parseInt(
                        transactionMatcher.group(1));

                String noKartu =
                        transactionMatcher.group(2);

                String tanggal =
                        transactionMatcher.group(3);

                BigDecimal nominal =
                        new BigDecimal(
                                transactionMatcher.group(4)
                                        .replace(",", "")
                        );

                String terminal =
                        transactionMatcher.group(5).trim();

                String kode =
                        transactionMatcher.group(6);

                // Baris berikutnya
                String nextLine = reader.readLine();

                String waktu = null;
                String lokasi = "";

                if (nextLine != null) {

                    Matcher timeMatcher =
                            TIME_LINE.matcher(nextLine);

                    if (timeMatcher.matches()) {

                        waktu =
                                timeMatcher.group(1);

                        lokasi =
                                timeMatcher.group(2).trim();
                    }
                }

                Timestamp timestamp = null;

                if (waktu != null) {

                    timestamp = new Timestamp(
                            DATE_TIME_FORMAT.parse(
                                    tanggal + " " + waktu
                            ).getTime()
                    );
                }

                FleetTransaction trx = new FleetTransaction();

                trx.setNoUrut(noUrut);
                trx.setNoKartu(noKartu);
                trx.setTimestamp(timestamp);
                trx.setNominal(nominal);
                trx.setTerminal(terminal);
                trx.setKode(kode);
                trx.setLokasi(lokasi);

                result.add(trx);
            }
        }

        return result;
    }

    public static class FleetTransaction {

        private int noUrut;
        private String noKartu;
        private Timestamp timestamp;
        private BigDecimal nominal;
        private String terminal;
        private String kode;
        private String lokasi;

        public void setNoUrut(int noUrut) {
            this.noUrut = noUrut;
        }

        public void setNoKartu(String noKartu) {
            this.noKartu = noKartu;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public void setNominal(BigDecimal nominal) {
            this.nominal = nominal;
        }

        public void setTerminal(String terminal) {
            this.terminal = terminal;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public void setLokasi(String lokasi) {
            this.lokasi = lokasi;
        }

        public int getNoUrut() {
            return noUrut;
        }

        public String getNoKartu() {
            return noKartu;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public BigDecimal getNominal() {
            return nominal;
        }

        public String getTerminal() {
            return terminal;
        }

        public String getKode() {
            return kode;
        }

        public String getLokasi() {
            return lokasi;
        }
    }
}