package edu.rosehulman.rafinder.model.dummy;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.rosehulman.rafinder.model.DutyRosterItem;
import edu.rosehulman.rafinder.model.RoomEntry;
import edu.rosehulman.rafinder.model.person.Administrator;
import edu.rosehulman.rafinder.model.person.Employee;
import edu.rosehulman.rafinder.model.person.Resident;
import edu.rosehulman.rafinder.model.person.ResidentAssistant;
import edu.rosehulman.rafinder.model.person.SophomoreAdvisor;

public class DummyData {
    private static Employee RA1 = new ResidentAssistant("Armin Arlert", "brainiac@wallshina.com", 2, "Lakeside", "1-(555)-555-5555", 201, "In My room", "Come hang out!");
    private static Employee RA1b = new ResidentAssistant("Eren Yeager", "shifter@wallrose.com", 2, "Lakeside", "1-(555)-555-5556", 202, "In Class", "");
    private static Employee RA2 = new ResidentAssistant("Barty Crouch", "crazy4voldy@azkaban.net", 3, "Lakeside", "1-(555)-555-5556", 302, "Off campus", "Wibbly-Wobbly, Death-Eatery stuff to do!");
    private static Employee RA3 = new ResidentAssistant("Calcifer", "scaryPowerfulFireDemon@howl.org", 3, "Lakeside", "1-(555)-555-5557", 304, "In the Union", "Have a meeting");

    private static Resident resfa = new Resident("Arya Stark");
    private static Resident resfb = new Resident("Beifong Toph");
    private static Resident resfc = new Resident("Carol Marcus");
    private static Resident resfd = new Resident("Daenerys Targaryen");

    private static Resident[] room1 = { resfa, resfb, resfc, resfd };

    private static Employee resfe = new SophomoreAdvisor("Eowyn Dernhelm", "iAmNoMan@rohan.com", 1, "Lakeside", "1-(555)-555-5561", 104, "At the Library", "I'm studying. Do not disturb me.");
    private static Employee resff = new SophomoreAdvisor("Fem!Shep", "notDeadYet@normandy.exonet.org", 1, "Lakeside", "1-(555)-555-5562", 104, "In my room", "Busy studying.");
    private static Resident resfg = new Resident("Ginny Weasley");
    private static Resident resfh = new Resident("Holo the Wise Wolf");

    private static Resident[] room4 = { resfe, resff, resfg, resfh };

    private static Resident resfi = new Resident("Ilyasviel von Einzbern");
    private static Resident resfj = new Resident("Juliet Butler");
    private static Resident resfk = new Resident("Kasumi Goto");
    private static Resident resfl = new Resident("Luna Lovegood");
    private static Resident resfm = new Resident("Mikasa Ackerman");
    private static Resident[] room6 = { resfi, resfj, resfk, resfl, resfm };

    private static Resident resfn = new Resident("Nymphadora Tonks");
    private static Resident resfo = new Resident("Osha the Wildling");
    private static Resident resfp = new Resident("Petunia Dursley");
    private static Employee resfq = new SophomoreAdvisor("Harley Quinn", "jesterAndJoker@gothamcity.net", 1, "Lakeside", "1-(555)-555-5561", 109, "SRC", "Gotta be strong for Joker!");
    private static Resident[] room9 = { resfn, resfo, resfp, resfq };

    private static Resident resfr = new Resident("Rin Tohsaka");
    private static Resident resfs = new Resident("Sophie Hatter");
    private static Resident resft = new Resident("Tali'Zorah vas Normandy");
    private static Resident resfu = new Resident("Uhura, Nyota");
    private static Resident resfv = new Resident("Asseylum Vers Allusia");
    private static Resident[] room11 = { resfr, resfs, resft, resfu, resfv };


    private static Resident resfw = new Resident("Wonder Woman");
    private static Resident resfx = new Resident("Xena, Warrior Princess");
    private static Resident resfy = new Resident("Princess Yue");
    private static Resident resfz = new Resident("Zoe Hange");
    private static Resident[] room13 = { resfw, resfx, resfy, resfz };


    private static Resident resma = new Resident("Avatar Aang");
    private static Resident resmb = new Resident("Bran Stark");
    private static Resident resmc = new Resident("Charles Beams");
    private static Resident[] room2 = { resma, resmb, resmc };

    private static Resident resmd = new Resident("The Eleventh Doctor");
    private static Resident resme = new Resident("Edward Elric");
    private static Resident resmf = new Resident("Fred Weasley");
    private static Resident resmg = new Resident("George Weasley");
    private static Resident[] room3 = { resmd, resme, resmf, resmg };

    private static Resident resmh = new Resident("Hikaru Sulu");
    private static Employee resmi = new SophomoreAdvisor("Ianto Jones", "battleButler@torchwood.com", 1, "Lakeside", "1-(555)-555-5560", 105, "In my room", "Do not disturb");
    private static Employee resmj = new SophomoreAdvisor("Jack Harkness", "wheresMyStopwatch@torchwood.com", 1, "Lakeside", "1-(555)-555-5559", 105, "In my room", "Do not disturb");
    private static Resident[] room5 = { resmh, resmi, resmj };

    private static Resident resmk = new Resident("Kraft Lawrence");
    private static Resident resml = new Resident("Levi Ackermann");
    private static Resident resmm = new Resident("Montgomery Scott");
    private static Resident resmn = new Resident("Nao Kaizuka");
    private static Resident[] room7 = { resmk, resml, resmm, resmn };

    private static Resident resmo = new Resident("Oberyn Martell");
    private static Resident resmp = new Resident("Pavel Chekov");
    private static Resident resmq = new Resident("Quirinus Quirrell");
    private static Resident resmr = new Resident("Rory Williams");
    private static Resident[] room8 = { resmo, resmp, resmq, resmr };

    private static Employee resms = new ResidentAssistant("Spock Prime", "spock@enterprise.starfleet.com", 1, "Lakeside", "1-(555)-555-5558", 110, "In the Academic Buildings", "Seek me out, should you need my assistance");
    private static Resident resmt = new Resident("Tyrion Lannister");
    private static Resident resmu = new Resident("Urdnot Wrex");
    private static Resident[] room10 = { resms, resmt, resmu };

    private static Resident resmv = new Resident("Van Hohenhein");
    private static Resident resmw = new Resident("Wilfred Mott");
    private static Resident resmx = new Resident("Xenophilius Lovegood");
    private static Resident resmy = new Resident("Professor Yana");
    private static Resident resmz = new Resident("Fire Lord Zuko");

    private static Employee gandalf = new Administrator("Gandalf the Grey", "disturberOfThePeace@arda.net", "Office of Residence Life", "1-(555)-867-5309", 100, "At the Library", "I am reading. Disturb me only if you must.");

    private static Resident[] room12 = { resmv, resmw, resmx, resmy, resmz };

    private static final List<RoomEntry> rooms;
    private static final List<DutyRosterItem> dutyRoster;

    static {
        rooms = Arrays.asList(
                new RoomEntry("Lakeside", "101", room1),
                new RoomEntry("Lakeside", "102", room2),
                new RoomEntry("Lakeside", "103", room3),
                new RoomEntry("Lakeside", "104", room4),
                new RoomEntry("Lakeside", "105", room5),
                new RoomEntry("Lakeside", "106", room6),
                new RoomEntry("Lakeside", "107", room7),
                new RoomEntry("Lakeside", "108", room8),
                new RoomEntry("Lakeside", "109", room9),
                new RoomEntry("Lakeside", "110", room10),
                new RoomEntry("Lakeside", "111", room11),
                new RoomEntry("Lakeside", "112", room12),
                new RoomEntry("Lakeside", "113", room13));

        dutyRoster = Arrays.asList(
                new DutyRosterItem(new LocalDate(2015, 7, 10), RA1, RA2),
                new DutyRosterItem(new LocalDate(2015, 7, 17), RA3, RA1),
                new DutyRosterItem(new LocalDate(2015, 7, 24), RA2, RA3));
    }

    public static List<DutyRosterItem> getDutyRoster() {
        return dutyRoster;
    }

    public static List<RoomEntry> getRooms() {
        return rooms;
    }

    public static List<Employee> getMyRAs() {
        return Collections.singletonList(resms);
    }

    public static List<Employee> getMySAs() {
        return Arrays.asList(resfe, resff, resfq, resmi, resmj);
    }


    public static List<Employee> getMyHallResLife() {
        return Arrays.asList(RA1, RA1b, RA2, RA3);
    }

    public static List<Employee> getEmergencyContacts() {
        return Arrays.asList(resms, RA1, RA1b, RA2, RA3, gandalf);
    }


}
