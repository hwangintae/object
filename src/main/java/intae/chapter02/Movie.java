package intae.chapter02;

public class Movie {
    private Money fee;

    public Movie(Money fee) {
        this.fee = fee;
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) {
        return fee;
    }
}
