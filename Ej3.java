import java.util.ArrayList;
import java.util.List;

public class Ej3 {
    private static final int MAX_BOXES_STACK = 5;
    private static final int MAX_TOTAL_BOXES = 16;
    private static final int MAX_TOTAL_STACKS = 8;
    private static final int MIN_TOTAL_STACKS = 2;
    private static final int MIN_TOTAL_BOXES = 1;
    private static final int MAX_COMMANDS = 100;

    private static boolean isBalanced(int[] boxes, int[] target) {
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i] != target[i]) {
                return false;
            }
        }
        return true;
    }

    private static String moveTo(int clawPos, int targetPos) {
        if (clawPos < targetPos) {
            return "RIGHT";
        } else if (clawPos > targetPos) {
            return "LEFT";
        }
        return null;
    }


    public static List<String> solve(int clawPos, int[] boxes, int boxInClaw) {
        int numStacks = boxes.length;
        int commandsCount = 0;
        int totalBoxes = 0;

        for (int box : boxes) {
            totalBoxes += box;
        }
        totalBoxes += boxInClaw;

        if (MIN_TOTAL_BOXES > totalBoxes || totalBoxes > MAX_TOTAL_BOXES) {
            throw new IllegalArgumentException("Wrong number of boxes, it has to be between " + MIN_TOTAL_BOXES + " and " + MAX_TOTAL_BOXES);
        }

        if (MIN_TOTAL_STACKS > numStacks || numStacks > MAX_TOTAL_STACKS) {
            throw new IllegalArgumentException("Wrong number of stacks, it has to be between " + MIN_TOTAL_STACKS + " and " + MAX_TOTAL_STACKS);
        }

        int targetHeight = totalBoxes / numStacks;
        int extraBoxes = totalBoxes % numStacks;

        if (targetHeight > MAX_BOXES_STACK) {
            throw new IllegalArgumentException("The number of boxes is too large to apply in stack of " + MAX_BOXES_STACK + " boxes.");
        }

        int[] target = new int[numStacks];
        for (int i = 0; i < numStacks; i++) {
            if (targetHeight + 1 > MAX_BOXES_STACK)
                target[i] = targetHeight;
            else
                target[i] = (i < extraBoxes) ? targetHeight + 1 : targetHeight;
        }

        List<String> commands = new ArrayList<>();

        while (!isBalanced(boxes, target) && commandsCount < MAX_COMMANDS) {
            if (boxInClaw == 0) {
                for (int i = 0; i < numStacks; i++) {
                    if (boxes[i] > target[i]) {
                        String moveCommand = moveTo(clawPos, i);
                        if (moveCommand != null) {
                            commands.add(moveCommand);
                            if (moveCommand.equals("RIGHT")) {
                                clawPos++;
                            } else {
                                clawPos--;
                            }
                        } else {
                            commands.add("PICK");
                            boxes[i]--;
                            boxInClaw = 1;
                        }
                        commandsCount++;
                        break;
                    }
                }
            } else {
                int i = 0;
                for (; i < numStacks; i++) {
                    if (boxes[i] < target[i]) {
                        String moveCommand = moveTo(clawPos, i);
                        if (moveCommand != null) {
                            commands.add(moveCommand);
                            if (moveCommand.equals("LEFT")) {
                                clawPos--;
                            } else {
                                clawPos++;
                            }
                        } else {
                            commands.add("PLACE");
                            boxes[i]++;
                            boxInClaw = 0;
                        }
                        commandsCount++;
                        break;
                    }
                }
                if (boxInClaw != 0 && i >= boxes.length) {
                    commands.add("Lose Condition, can't place all boxes.");
                    return commands;
                }
            }
        }

        int allBoxesPlaced = 0;
        for (int box : boxes) {
            allBoxesPlaced += box;
        }

        if (allBoxesPlaced != totalBoxes) {
            commands.add("Lose Condition, can't place all boxes.");
        }

        if (commandsCount >= MAX_COMMANDS) {
            commands.add("Lose Condition, too many commands.");
        }
        return commands;
    }

    public static void main(String[] args) {
        System.out.println("Case 1:");
        int[] boxes = {3, 2, 1, 4};
        int clawPos = 1;
        int boxInClaw = 1;

        List<String> result = solve(clawPos, boxes, boxInClaw);

        for (String command : result) {
            System.out.println(command);
        }


        System.out.println("\nCase 2:");
        int[] boxes2 = {4, 7, 5};
        int clawPos2 = 1;
        int boxInClaw2 = 0;

        List<String> result2 = solve(clawPos2, boxes2, boxInClaw2);

        for (String command : result2) {
            System.out.println(command);
        }

    }
}
