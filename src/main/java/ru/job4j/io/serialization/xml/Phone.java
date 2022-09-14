package ru.job4j.io.serialization.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "phone")
public class Phone {
    @XmlAttribute
    private String model;

    @XmlAttribute
    private long imei;

    public Phone() {
    }

    public Phone(String model, long imei) {
        this.model = model;
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public long getImei() {
        return imei;
    }

    @Override
    public String toString() {
        return "Phone{" + "model=" + model + ", imei=" + imei + '}';
    }
}
