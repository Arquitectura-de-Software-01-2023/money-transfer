namespace PDFGenerator;

public record PostRequest
(
    string Transaction,
    string Origin,
    string Destination,
    decimal Amount
);
