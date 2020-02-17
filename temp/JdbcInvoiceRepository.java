package io.pivotal.pal.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcInvoiceRepository implements InvoiceRepository {
    private static Logger logger = LoggerFactory.getLogger(JdbcInvoiceRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcInvoiceRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Invoice> mapper = (rs, rowNum) -> new Invoice(
            rs.getInt("invoiceNumber"),
            rs.getString("caryOnOrCargoBooking"),
            rs.getString("settlement"),
            rs.getInt("size"),
            rs.getInt("charge"),
            rs.getString("flightCode"),
            rs.getString("customerCode"),
            rs.getString("dealer"),
            rs.getString("personInCharge"),
            rs.getDate("receiptDate"),
            rs.getDate("deliveryDate"),
            rs.getString("receiptVoucher"),
            rs.getString("invoiceType"),
            rs.getString("address"),
            rs.getString("goods"),
            rs.getString("client"),
            rs.getDate("requestDate"),
            rs.getString("fullName"),
            rs.getString("postalCode"),
            rs.getString("telephoneNumber"),
            rs.getString("destination"),
            rs.getString("position"),
            rs.getString("goodsCode"),
            rs.getString("goodsName"),
            rs.getString("pickup"),
            rs.getString("GolfOrSkiOrAirport"),
            rs.getString("clubNumber"),
            rs.getString("service")
    );
    private final ResultSetExtractor<Invoice> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;

    @Override
    public Invoice find(Integer invoiceNumber) {
        logger.debug("SELECT * FROM Invoice WHERE invoiceNumber = [{}]", invoiceNumber);
        Invoice iv = jdbcTemplate.query(
                "SELECT * FROM Invoice WHERE invoiceNumber = ?",
                new Object[]{invoiceNumber},
                extractor);
        if (iv != null){
            logger.debug("Invoice invoiceNumber[{}],{},{}", iv.getInvoiceNumber(), iv.getDeliveryDate(), iv.getInvoiceType());
        }else{
            logger.debug("Invoice is null.");
        }
        return iv;

//        @Override
//        public Invoice find(Long id) {
//            logger.debug("SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = [{}]", id);
//            Invoice iv = jdbcTemplate.query(
//                    "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
//                    new Object[]{id},
//                    extractor);
//            if (iv != null){
//                logger.debug("TimeEntry id[{}], Date[{}], pid[{}]", iv.getInvoiceNumber(), iv.getDeliveryDate(), iv.getInvoiceType());
//            }else{
//                logger.debug("TimeEntry is null.");
//            }
//            return iv;
//
//        return jdbcTemplate.query(
//            "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
//            new Object[]{id},
//            extractor);
    }


//    @Override
//    public Invoice create(Invoice invoice) {
//        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement statement = connection.prepareStatement(
//                "INSERT INTO time_entries (project_id, user_id, date, hours) " +
//                    "VALUES (?, ?, ?, ?)",
//                RETURN_GENERATED_KEYS
//            );
//
//            statement.setLong(1, invoice.getProjectId());
//            statement.setLong(2, invoice.getUserId());
//            statement.setDate(3, Date.valueOf(invoice.getDate()));
//            statement.setInt(4, invoice.getHours());
//
//            return statement;
//        }, generatedKeyHolder);
//
//        return find(generatedKeyHolder.getKey().longValue());
//    }
//
//    @Override
//    public List<TimeEntry> list() {
//        return jdbcTemplate.query("SELECT id, project_id, user_id, date, hours FROM time_entries", mapper);
//    }
//
//    @Override
//    public TimeEntry update(Long id, TimeEntry timeEntry) {
//        jdbcTemplate.update("UPDATE time_entries " +
//                "SET project_id = ?, user_id = ?, date = ?,  hours = ? " +
//                "WHERE id = ?",
//            timeEntry.getProjectId(),
//            timeEntry.getUserId(),
//            Date.valueOf(timeEntry.getDate()),
//            timeEntry.getHours(),
//            id);
//
//        return find(id);
//    }
//
//    @Override
//    public void delete(Long id) {
//        jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?", id);
//    }
//

}
