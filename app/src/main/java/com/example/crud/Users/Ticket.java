package com.example.crud.Users;

public class Ticket {

    private String  noTicket, problem, solve, lokasi, petugas, area, shift, jam;

    public Ticket() {
    }

    public Ticket(String noTicket, String problem, String solve, String lokasi, String petugas, String area, String shift, String jam) {

        this.noTicket = noTicket;
        this.problem = problem;
        this.solve = solve;
        this.lokasi = lokasi;
        this.petugas = petugas;
        this.area = area;
        this.shift = shift;
        this.jam = jam;
    }



    public String getNoTicket() {
        return noTicket;
    }

    public void setNoTicket(String noTicket) {
        this.noTicket = noTicket;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolve() {
        return solve;
    }

    public void setSolve(String solve) {
        this.solve = solve;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getPetugas() {
        return petugas;
    }

    public void setPetugas(String petugas) {
        this.petugas = petugas;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
}
