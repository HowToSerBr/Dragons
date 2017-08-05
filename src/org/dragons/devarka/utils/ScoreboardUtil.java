package org.dragons.devarka.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtil {
    public static String[] cutUnranked(String[] content) {
        String[] elements = Arrays.copyOf(content, 16);
        if (elements[0] == null) {
            elements[0] = "Unamed board";
        }
        if (elements[0].length() > 32) {
            elements[0] = elements[0].substring(0, 32);
        }
        int i = 1;
        while (i < elements.length) {
            if (elements[i] != null && elements[i].length() > 40) {
                elements[i] = elements[i].substring(0, 40);
            }
            ++i;
        }
        return elements;
    }

    public static String cutRankedTitle(String title) {
        if (title == null) {
            return "Unamed board";
        }
        if (title.length() > 32) {
            return title.substring(0, 32);
        }
        return title;
    }

    public static HashMap<String, Integer> cutRanked(HashMap<String, Integer> content) {
        HashMap<String, Integer> elements = new HashMap<String, Integer>();
        elements.putAll(content);
        while (elements.size() > 15) {
            String minimumKey = (String)elements.keySet().toArray()[0];
            int minimum = elements.get(minimumKey);
            for (String string : elements.keySet()) {
                if (elements.get(string) >= minimum && (elements.get(string) != minimum || string.compareTo(minimumKey) >= 0)) continue;
                minimumKey = string;
                minimum = elements.get(string);
            }
            elements.remove(minimumKey);
        }
        for (String s : elements.keySet()) {
            if (s == null || s.length() <= 40) continue;
            int value = elements.get(s);
            elements.remove(s);
            elements.put(s.substring(0, 40), value);
        }
        return elements;
    }

    public static boolean unrankedSidebarDisplay(Player p, String[] elements) {
        elements = ScoreboardUtil.cutUnranked(elements);
        try {
            if (p.getScoreboard() == null || p.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard() || p.getScoreboard().getObjectives().size() != 1) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            if (p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)) == null) {
                p.getScoreboard().registerNewObjective(p.getUniqueId().toString().substring(0, 16), "dummy");
                p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(elements[0]);
            int i = 1;
            while (i < elements.length) {
                if (elements[i] != null && p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).getScore() != 16 - i) {
                    p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).setScore(16 - i);
                    for (Object string : p.getScoreboard().getEntries()) {
                        if (p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore((String)string).getScore() != 16 - i || string.equals(elements[i])) continue;
                        p.getScoreboard().resetScores((String)string);
                    }
                }
                ++i;
            }
            for (String entry : p.getScoreboard().getEntries()) {
                boolean toErase = true;
                String[] arrayOfString = elements;
                int j = arrayOfString.length;
                int i2 = 0;
                while (i2 < j) {
                    String element = arrayOfString[i2];
                    if (element != null && element.equals(entry) && p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore(entry).getScore() == 16 - Arrays.asList(elements).indexOf(element)) {
                        toErase = false;
                        break;
                    }
                    ++i2;
                }
                if (!toErase) continue;
                p.getScoreboard().resetScores(entry);
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean unrankedSidebarDisplay(Collection<Player> players, String[] elements) {
        for (Player player : players) {
            if (ScoreboardUtil.unrankedSidebarDisplay(player, elements)) continue;
            return false;
        }
        return true;
    }

    public static boolean unrankedSidebarDisplay(Collection<Player> players, String[] elements, Scoreboard board) {
        try {
            String objName = "COLLAB-SB-WINTER";
            if (board == null) {
                board = Bukkit.getScoreboardManager().getNewScoreboard();
            }
            elements = ScoreboardUtil.cutUnranked(elements);
            for (Player player : players) {
                if (player.getScoreboard() == board) continue;
                player.setScoreboard(board);
            }
            if (board.getObjective(objName) == null) {
                board.registerNewObjective(objName, "dummy");
                board.getObjective(objName).setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(elements[0]);
            int i = 1;
            while (i < elements.length) {
                if (elements[i] != null && board.getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).getScore() != 16 - i) {
                    board.getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).setScore(16 - i);
                    for (Object string : board.getEntries()) {
                        if (board.getObjective(objName).getScore((String)string).getScore() != 16 - i || string.equals(elements[i])) continue;
                        board.resetScores((String)string);
                    }
                }
                ++i;
            }
            for (String entry : board.getEntries()) {
                boolean toErase = true;
                String[] arrayOfString = elements;
                int j = arrayOfString.length;
                int i2 = 0;
                while (i2 < j) {
                    String element = arrayOfString[i2];
                    if (element != null && element.equals(entry) && board.getObjective(objName).getScore(entry).getScore() == 16 - Arrays.asList(elements).indexOf(element)) {
                        toErase = false;
                        break;
                    }
                    ++i2;
                }
                if (!toErase) continue;
                board.resetScores(entry);
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rankedSidebarDisplay(Player p, String title, HashMap<String, Integer> elements) {
        try {
            title = ScoreboardUtil.cutRankedTitle(title);
            elements = ScoreboardUtil.cutRanked(elements);
            if (p.getScoreboard() == null || p.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard() || p.getScoreboard().getObjectives().size() != 1) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            if (p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)) == null) {
                p.getScoreboard().registerNewObjective(p.getUniqueId().toString().substring(0, 16), "dummy");
                p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(title);
            for (String string2 : elements.keySet()) {
                if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(string2).getScore() == elements.get(string2).intValue()) continue;
                p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(string2).setScore(elements.get(string2).intValue());
            }
            for (String string2 : p.getScoreboard().getEntries()) {
                if (elements.keySet().contains(string2)) continue;
                p.getScoreboard().resetScores(string2);
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rankedSidebarDisplay(Collection<Player> players, String title, HashMap<String, Integer> elements) {
        for (Player player : players) {
            if (ScoreboardUtil.rankedSidebarDisplay(player, title, elements)) continue;
            return false;
        }
        return true;
    }

    public static boolean rankedSidebarDisplay(Collection<Player> players, String title, HashMap<String, Integer> elements, Scoreboard board) {
        try {
            title = ScoreboardUtil.cutRankedTitle(title);
            elements = ScoreboardUtil.cutRanked(elements);
            String objName = "COLLAB-SB-WINTER";
            if (board == null) {
                board = Bukkit.getScoreboardManager().getNewScoreboard();
            }
            for (Player player : players) {
                if (player.getScoreboard() == board) continue;
                player.setScoreboard(board);
            }
            if (board.getObjective(objName) == null) {
                board.registerNewObjective(objName, "dummy");
                board.getObjective(objName).setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(title);
            for (String string2 : elements.keySet()) {
                if (board.getObjective(DisplaySlot.SIDEBAR).getScore(string2).getScore() == elements.get(string2).intValue()) continue;
                board.getObjective(DisplaySlot.SIDEBAR).getScore(string2).setScore(elements.get(string2).intValue());
            }
            for (String string2 : board.getEntries()) {
                if (elements.keySet().contains(string2)) continue;
                board.resetScores(string2);
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

