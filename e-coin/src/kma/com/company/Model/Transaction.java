package kma.com.company.Model;

import sun.security.provider.DSAPublicKeyImpl;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

public class Transaction implements Serializable {
    private byte[] from; //contain the public keys/addresses of the account that sends  the coins
    private String fromFX;
    private byte[] to; //contain the public keys/addresses of the account that receives the coins
    private String toFX;
    private Integer value;  //the amount of coins that will be sen
    private String timeStamp;
    private byte[] signature;//contain the encrypted information of all the fields, and it will be used to verify the validity of the transaction
    private String signatureFX;
    private Integer ledgerId;

    //Constructor for loading with existing signature
    public Transaction(byte[] from, byte[] to, Integer value, byte[] signature, Integer ledgerId,
                       String timeStamp) {
        Base64.Encoder encoder = Base64.getEncoder();
        this.from = from;
        this.fromFX = encoder.encodeToString(from);
        this.to = to;
        this.toFX = encoder.encodeToString(to);
        this.value = value;
        this.signature = signature;
        this.signatureFX = encoder.encodeToString(signature);
        this.ledgerId = ledgerId;
        this.timeStamp = timeStamp;
    }

    //Constructor for creating a new transaction and signing it.
    public Transaction(Wallet fromWallet, byte[] toAddress, Integer value, Integer ledgerId
                       , Signature signing) throws InvalidKeyException, SignatureException {
        Base64.Encoder encoder = Base64.getEncoder();
        this.from = fromWallet.getPublicKey().getEncoded();
        this.fromFX = encoder.encodeToString(fromWallet.getPublicKey().getEncoded());
        this.to = toAddress;
        this.toFX = encoder.encodeToString(toAddress);
        this.value = value;
        this.ledgerId = ledgerId;
        this.timeStamp = LocalDateTime.now().toString();
        signing.initSign(fromWallet.getPrivateKey());
        String sr = this.toString();
        signing.update(sr.getBytes());
        this.signature = signing.sign();
        this.signatureFX = encoder.encodeToString(this.signature);
    }

    public Boolean isVerified (Signature signing) throws InvalidKeyException, SignatureException {
        signing.initVerify(new DSAPublicKeyImpl(this.getFrom()));
        signing.update(this.toString().getBytes());
        return signing.verify(this.signature);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from=" + Arrays.toString(from) +
                ", to=" + Arrays.toString(to) +
                ", value=" + value +
                ", timeStamp= " + timeStamp +
                ", ledgerId=" + ledgerId +
                '}';
    }

    public byte[] getFrom() {
        return from;
    }

    public void setFrom(byte[] from) {
        this.from = from;
    }

    public String getFromFX() {
        return fromFX;
    }

    public byte[] getTo() {
        return to;
    }

    public void setTo(byte[] to) {
        this.to = to;
    }

    public String getToFX() {
        return toFX;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public byte[] getSignature() {
        return signature;
    }

    public String getSignatureFX() {
        return signatureFX;
    }

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Arrays.equals(getSignature(), that.getSignature());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getSignature());
    }
}
