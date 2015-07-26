package edu.rosehulman.rafinder.model.dummy;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.model.DutyRosterItem;
import edu.rosehulman.rafinder.model.RoomEntry;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.Resident;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;
import edu.rosehulman.rafinder.model.person.SophomoreAdvisor;

/**
 * Created by daveyle on 7/25/2015.
 */
public class DummyData {
    //public Employee(String name, String room, String floor, String phoneNumber, String dutyStatus)
    //public Resident(String name, String room)
    //public DutyRosterItem(LocalDate friday, Employee friDuty, Employee satDuty)
    //public Floor(int number, List<RoomEntry> rooms, int lobbyAfterRoomNumber)
    //public Hall(String name, Floor[] floors)
    //public RoomEntry(Resident[] residents, String hallName, String roomNumber)
    private static ResidentAssistant RA1= new ResidentAssistant("Armin Arlert", "201","2", "BSB", "1-(555)-555-5555", "brainiac@wallshina.com","My room");
    private static ResidentAssistant RA1b= new ResidentAssistant("Eren Yeager", "202", "2","BSB", "1-(555)-555-5556", "shifter@wallrose.com", "Class");
    private static ResidentAssistant RA2 = new ResidentAssistant("Barty Crouch, Jr.", "302","3", "BSB", "1-(555)-555-5556", "crazy4voldy@azkaban.net","Off campus");
    private static ResidentAssistant RA3= new ResidentAssistant("Calcifer", "304", "3","BSB", "1-(555)-555-5557", "scaryPowerfulFireDemon@howl.org", "Union");

    private static Resident resfa=new Resident("Arya Stark", "101", "BSB");
    private static Resident resfb=new Resident("Beifong Toph", "101", "BSB");
    private static Resident resfc=new Resident("Carol Marcus", "101", "BSB");
    private static Resident resfd=new Resident("Daenerys Targaryen", "101", "BSB");

    private static Resident[] room1={resfa, resfb, resfc, resfd};

    private static SophomoreAdvisor resfe=new SophomoreAdvisor("Eowyn Dernhelm", "104", "1", "BSB", "1-(555)-555-5561", "iAmNoMan@rohan.com", "Library");
    private static SophomoreAdvisor resff=new SophomoreAdvisor("Fem!Shep", "104", "1", "BSB", "1-(555)-555-5562","notDeadYet@normandy.exonet.org","In my room");
    private static Resident resfg=new Resident("Ginny Weasley", "104", "BSB");
    private static Resident resfh= new Resident("Holo the Wise Wolf", "104", "BSB");

    private static Resident[] room4={resfe, resff, resfg, resfh};

    private static Resident resfi= new Resident("Ilyasviel von Einzbern", "106", "BSB");
    private static Resident resfj= new Resident("Juliet Butler", "106", "BSB");
    private static Resident resfk=new Resident("Kasumi Goto", "106", "BSB");
    private static Resident resfl=new Resident("Luna Lovegood", "106", "BSB");
    private static Resident resfm=new Resident("Mikasa Ackerman", "106", "BSB");
    private static Resident[] room6={resfi, resfj, resfk, resfl, resfm};

    private static Resident resfn=new Resident("Nymphadora Tonks", "109", "BSB");
    private static Resident resfo=new Resident("Osha the Wildling", "109", "BSB");
    private static Resident resfp=new Resident("Petunia Dursley", "109", "BSB");
    private static SophomoreAdvisor resfq=new SophomoreAdvisor("Quinn, Harley", "109", "1", "BSB", "1-(555)-555-5561", "jesterAndJoker@gothamcity.net", "SRC");
    private static Resident[] room9={resfn, resfo, resfp, resfq};

    private static Resident resfr=new Resident("Rin Tohsaka", "111", "BSB");
    private static Resident resfs=new Resident("Sophie Hatter", "111", "BSB");
    private static Resident resft=new Resident("Tali'Zorah vas Normandy", "111", "BSB");
    private static Resident resfu=new Resident("Uhura, Nyota", "111", "BSB");
    private static Resident resfv=new Resident("Vers Allusia, Asseylum", "111", "BSB");
    private static Resident[] room11={resfr, resfs, resft, resfu, resfv};
    
    
    private static Resident resfw=new Resident("Wonder Woman", "113", "BSB");
    private static Resident resfx=new Resident("Xena, Warrior Princess", "113", "BSB");
    private static Resident resfy=new Resident("Yue, Princess", "113", "BSB");
    private static Resident resfz=new Resident("Zoe Hange", "113", "BSB");
    private static Resident[] room13={resfw, resfx, resfy, resfz};


    private static Resident resma=new Resident("Aang, Avatar", "102", "BSB");
    private static Resident resmb=new Resident("Bran Stark", "102", "BSB");
    private static Resident resmc=new Resident("Charles Beams", "102", "BSB");
    private static Resident[] room2={resma, resmb, resmc};

    private static Resident resmd= new Resident("The Doctor, Eleventh", "103", "BSB");
    private static Resident resme= new Resident("Edward Elric", "103", "BSB");
    private static Resident resmf= new Resident("Fred Weasley", "103", "BSB");
    private static Resident resmg= new Resident("George Weasley", "103", "BSB");
    private static Resident[] room3={resmd, resme, resmf, resmg};

    private static Resident resmh=new Resident("Hikaru Sulu", "105", "BSB");
    private static SophomoreAdvisor resmi=new SophomoreAdvisor("Ianto Jones", "105", "1", "BSB", "1-(555)-5560", "battleButler@torchwood.com", "In my room--Do not disturb");
    private static SophomoreAdvisor resmj=new SophomoreAdvisor("Jack Harkness, Capt.", "105", "1","BSB", "1-(555)-5559","wheresMyStopwatch@torchwood.com", "In my room--Do not disturb");
    private static Resident[] room5={resmh, resmi, resmj};

    private static Resident resmk=new Resident("Kraft Lawrence", "107", "BSB");
    private static Resident resml=new Resident("Levi Ackermann", "107", "BSB");
    private static Resident resmm=new Resident("Montegomery Scott", "107", "BSB");
    private static Resident resmn=new Resident("Nao Kaizuka", "107", "BSB");
    private static Resident[] room7={resmk, resml, resmm, resmn};

    private static Resident resmo=new Resident("Oberyn Martell", "108", "BSB");
    private static Resident resmp=new Resident("Pavel Chekov", "108", "BSB");
    private static Resident resmq=new Resident("Quirinus Quirrell", "108", "BSB");
    private static Resident resmr=new Resident("Rory Williams", "108", "BSB");
    private static Resident[] room8={resmo, resmp, resmq, resmr};
    
    private static ResidentAssistant resms=new ResidentAssistant("Spock Prime", "110",  "1","BSB", "1-(555)-555-5558","spock@enterprise.starfleet.com", "Academic Buildings");
    private static Resident resmt=new Resident("Tyrion Lannister", "110", "BSB");
    private static Resident resmu=new Resident("Urdnot Wrex", "110", "BSB");
    private static Resident[] room10={resms, resmt, resmu};
    
    private static Resident resmv=new Resident("Van Hohenhein", "112", "BSB");
    private static Resident resmw=new Resident("Wilfred Mott", "112", "BSB");
    private static Resident resmx=new Resident("Xenophilius Lovegood", "112", "BSB");
    private static Resident resmy=new Resident("Yana, Professor", "112", "BSB");
    private static Resident resmz=new Resident("Zuko, Fire Lord", "112", "BSB");


    private static Resident[] room12={resmv, resmw, resmx, resmy, resmz};

    public static List<DutyRosterItem> getDutyRoster(){
        List<DutyRosterItem> dutyRoster= new ArrayList<DutyRosterItem>();
        DutyRosterItem dutyRosterItem1= new DutyRosterItem(new LocalDate(2015, 7, 10), RA1, RA2);
        DutyRosterItem dutyRosterItem2= new DutyRosterItem(new LocalDate(2015, 7, 17), RA3, RA1);
        DutyRosterItem dutyRosterItem3= new DutyRosterItem(new LocalDate(2015, 7, 24), RA2, RA3);
        dutyRoster.add(dutyRosterItem1);
        dutyRoster.add(dutyRosterItem2);
        dutyRoster.add(dutyRosterItem3);
        return dutyRoster;
    }
    public static List<RoomEntry> getRooms(){
        List<RoomEntry> rooms=new ArrayList<RoomEntry>();
        rooms.add(new RoomEntry(room1, "BSB", "101"));
        rooms.add(new RoomEntry(room2, "BSB", "102"));
        rooms.add(new RoomEntry(room3, "BSB", "103"));
        rooms.add(new RoomEntry(room4, "BSB", "104"));
        rooms.add(new RoomEntry(room5, "BSB", "105"));
        rooms.add(new RoomEntry(room6, "BSB", "106"));
        rooms.add(new RoomEntry(room7, "BSB", "107"));
        rooms.add(new RoomEntry(room8, "BSB", "108"));
        rooms.add(new RoomEntry(room9, "BSB", "109"));
        rooms.add(new RoomEntry(room10, "BSB", "110"));
        rooms.add(new RoomEntry(room11, "BSB", "111"));
        rooms.add(new RoomEntry(room12, "BSB", "112"));
        rooms.add(new RoomEntry(room13, "BSB", "113"));
        return rooms;
        

    }
    public static List<ResidentAssistant> getMyRAs(){
        List<ResidentAssistant> ras= new ArrayList<ResidentAssistant>();
        ras.add(resms);

        return ras;
    }
    public static List<SophomoreAdvisor> getMySAs(){
        List<SophomoreAdvisor> ras= new ArrayList<SophomoreAdvisor>();
        ras.add(resfe);
        ras.add(resff);
        ras.add(resfq);
        ras.add(resmi);
        ras.add(resmj);
        return ras;
    }
    public static List<Employee> getMyHallResLife(){
        List<Employee> ras= new ArrayList<Employee>();
        ras.add(RA1);
        ras.add(RA1b);
        ras.add(RA2);
        ras.add(RA3);
        return ras;
    }



}
