package ru.job4j.io.serialization.xml;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

@XmlRootElement(name = "me")
@XmlAccessorType(XmlAccessType.FIELD)
public class Me {

    @XmlAttribute
    private String name;
    @XmlAttribute
    private int age;
    @XmlAttribute
    private boolean onLine;
    @XmlElementWrapper(name = "jobs")
    @XmlElement(name = "job")
    private String[] jobsArray;
    private Phone phone;

    public Me() {
    }

    public Me(String name,
              int age,
              boolean onLine,
              String[] jobsArray,
              Phone phone) {
        this.name = name;
        this.age = age;
        this.onLine = onLine;
        this.jobsArray = jobsArray;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isOnLine() {
        return onLine;
    }

    @Override
    public String toString() {
        return "Me {"
                + "name=" + name
                + ", age=" + age
                + ", online=" + onLine
                + ", statuses=" + Arrays.toString(jobsArray)
                + ", " + phone.toString()
                + '}';
    }

    public static void main(String[] args) throws JAXBException {
        final String[] arrayJobs = new String[]{
                "кровельщик", "чертёжник", "техник"

        };
        final Phone phone = new Phone("Nokia", 9999999999L);
        final Me me = new Me(
                "Andrey", 30, false, arrayJobs, phone
        );
        JAXBContext context = JAXBContext.newInstance(Me.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(me, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            Me rsl = (Me) unmarshaller.unmarshal(reader);
            System.out.println(rsl);
        }
        System.out.println(System.lineSeparator() + "JSON example:");
        JSONObject jsonPhone = new JSONObject(phone);
        jsonPhone.put("model", phone.getModel());
        jsonPhone.put("imei", phone.getImei());
        JSONObject jsonMe = new JSONObject();
        JSONArray jsonJobs = new JSONArray(Arrays.stream(arrayJobs).toList());
        jsonMe.put("name", me.getName());
        jsonMe.put("age", me.getAge());
        jsonMe.put("onLine", me.isOnLine());
        jsonMe.put("jobs", jsonJobs);
        jsonMe.put("phone", jsonPhone);
        System.out.println("by put: " + jsonMe);
        System.out.println("from object: " + new JSONObject(me));
        System.out.println(jsonPhone);
    }
}