public class BorrowRecord {
    private Member member;
    private Book book;

    public BorrowRecord(Member member, Book book) {
        this.member = member;
        this.book = book;
    }

    public void execute() {
        member.borrowBook(book);
    }
}
