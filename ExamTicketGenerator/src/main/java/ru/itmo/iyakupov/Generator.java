package ru.itmo.iyakupov;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

public class Generator {
    public static void main(String[] args) throws IOException {
        new Generator().run();
    }

    private List<BucketDef> defs = new ArrayList<>();
    private List<BucketState> states = new ArrayList<>();
    private String sessionFileName = "session.json";
    private final Random random = new SecureRandom();

    private void run() throws IOException {
        random.setSeed(System.nanoTime());

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter command: q/readDef/setSessFile/initSession/loadSession/saveSession/getTicket");
            String cmdString;
            while (!"q".equalsIgnoreCase(cmdString = scanner.nextLine())) {
                final String[] cmd = cmdString.split(" ");
                switch (cmd[0]) {
                    case "readDef":
                    case "rd":
                        if (cmd.length > 1)
                            readDef(cmd[1]);
                        else
                            System.err.println("Not enough arguments");
                        break;
                    case "initSession":
                    case "init":
                        initSession();
                        break;
                    case "loadSession":
                        loadSession(sessionFileName);
                        break;
                    case "saveSession":
                        saveSession(sessionFileName);
                        break;
                    case "setSessFile":
                        if (cmd.length > 1)
                            sessionFileName = cmd[1];
                        else
                            System.err.println("Not enough arguments");
                        break;
                    case "getTicket":
                    case "gt":
                        if (cmd.length > 1)
                            getTicket(cmd[1]);
                        else
                            System.err.println("Not enough arguments");
                        break;
                }
            }
        }
    }

    private void getTicket(String s) throws IOException {
        try {
            final int nTickets = Integer.parseInt(s);
            final SortedSet<BucketState> suitableBuckets = new TreeSet<>();
            states.stream().filter(state -> !state.isExhausted()).forEach(suitableBuckets::add);

            for (int i = 0; i < nTickets; ++i) {
                int bucketCounter = random.nextInt(suitableBuckets.size());
                for (BucketState suitableBucket : suitableBuckets) {
                    if (bucketCounter == 0) {
                        suitableBuckets.remove(suitableBucket);
                        System.out.println(String.format("Question %d.%d", suitableBucket.getBucketDef().getId() + 1,
                                suitableBucket.takeRandomMember() + 1));
                        break;
                    } else {
                        --bucketCounter;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Operation failed. Wrong ticket count?");
            e.printStackTrace();
        } finally {
            saveSession(sessionFileName);
        }
    }

    private void initSession() {
        states = new ArrayList<>();
        for (BucketDef def: defs) {
            states.add(new BucketState(def));
        }
        System.out.println("Init done. Buckets count: " + states.size());
    }

    private void loadSession(String fileName) throws IOException {
        final ListStorage<BucketState> storage = new ListStorage<>(new TypeToken<List<BucketState>>(){});
        storage.load(new File(fileName));
        this.states = storage.getElements();
        System.out.println("Load done. Buckets count: " + states.size());
    }

    private void saveSession(String fileName) throws IOException {
        final ListStorage<BucketState> storage = new ListStorage<>(new TypeToken<List<BucketState>>(){});
        storage.setElements(states);
        storage.store(new File(fileName));
        System.out.println("Saved");
    }

    private void readDef(String fileName) throws IOException {
        final ListStorage<BucketDef> storage = new ListStorage<>(new TypeToken<List<BucketDef>>(){});
        storage.load(new File(fileName));
        this.defs = storage.getElements();
        System.out.println("Read " + defs.size() + " definitions from " + fileName);
        System.out.println(defs);
        for (Object def : defs) {
            System.out.println(def.getClass());
        }
    }


}
