package kma.com.company.Model;

import sun.security.provider.DSAPublicKeyImpl;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Block implements Serializable {

    private byte[] prevHash; //contain the signature or, in other words, the encrypted data from the previous block
    private byte[] currHash; //contain the signature or, in other words, the encrypted data from this block
    private String timeStamp; //contain a timestamp of when this block was mined/finalized
    private byte[] minedBy; //contain the public key
    private Integer ledgerId = 1; // retrieve the correct corresponding ledger for this block (as the block number)
    private Integer miningPoints = 0; //  used to form the network consensus in regard to choosing this block’s miner
    private Double luck = 0.0;

    private ArrayList<Transaction> transactionLedger = new ArrayList<>();

    //This constructor is used when we retrieve it from the db
    public Block(byte[] prevHash, byte[] currHash, String timeStamp, byte[] minedBy,
                 Integer ledgerId, Integer miningPoints, Double luck,
                 ArrayList<Transaction> transactionLedger) {
        this.prevHash = prevHash;
        this.currHash = currHash;
        this.timeStamp = timeStamp;
        this.minedBy = minedBy;
        this.ledgerId = ledgerId;
        this.miningPoints = miningPoints;
        this.luck = luck;
        this.transactionLedger = transactionLedger;
    }

    //This constructor is used when we initiate it after retrieve.
    public Block(LinkedList<Block> currentBlockChain) {
        Block lastBlock = currentBlockChain.getLast();
        prevHash = lastBlock.getCurrHash();
        ledgerId = lastBlock.getLedgerId() + 1;
        luck = Math.random() * 1000000;
    }

    //This constructor is used only for creating the first block in the blockchain.
    public Block() {
        prevHash = new byte[]{0};
    }

    public Boolean isVerified(Signature signing)
            throws InvalidKeyException, SignatureException {
        signing.initVerify(new DSAPublicKeyImpl(this.minedBy));
        signing.update(this.toString().getBytes());
        return signing.verify(this.currHash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;
        Block block = (Block) o;
        return Arrays.equals(getPrevHash(), block.getPrevHash());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getPrevHash());
    }

    public byte[] getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(byte[] prevHash) {
        this.prevHash = prevHash;
    }

    public byte[] getCurrHash() {
        return currHash;
    }

    public void setCurrHash(byte[] currHash) {
        this.currHash = currHash;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public byte[] getMinedBy() {
        return minedBy;
    }

    public void setMinedBy(byte[] minedBy) {
        this.minedBy = minedBy;
    }

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    public ArrayList<Transaction> getTransactionLedger() {
        return transactionLedger;
    }

    public void setTransactionLedger(ArrayList<Transaction> transactionLedger) {
        this.transactionLedger = transactionLedger;
    }

    public Integer getMiningPoints() {
        return miningPoints;
    }

    public void setMiningPoints(Integer miningPoints) {
        this.miningPoints = miningPoints;
    }

    public Double getLuck() {
        return luck;
    }

    public void setLuck(Double luck) {
        this.luck = luck;
    }

    @Override
    public String toString() {
        return "Block{" +
                "prevHash=" + Arrays.toString(prevHash) +
                ", timeStamp='" + timeStamp + '\'' +
                ", minedBy=" + Arrays.toString(minedBy) +
                ", ledgerId=" + ledgerId +
                ", miningPoints=" + miningPoints +
                ", luck=" + luck +
                '}';
    }

}
