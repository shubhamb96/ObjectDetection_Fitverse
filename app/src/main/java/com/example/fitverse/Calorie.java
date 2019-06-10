package com.example.fitverse;

/*
This class is used to get the nutritional facts about the class
 */

public class Calorie {


    /**
     *
     * @param fruit
     * @param num
     * @return String of nutritional facts
     */
    public static String countcalorie(String fruit, int num) {
        String fname = "";
        String fact = "";
        double protein = 0;
        int calorie = 0;
        if (!fruit.isEmpty()) {

            switch (fruit) {
                case "banana":
                    fname = "Banana";
                    calorie = 89;
                    fact = "Free of Fat and Cholesterol";
                    protein = 1.1;
                    break;

                case "capsicum":
                    fname = "Capsicum";
                    calorie = 40;
                    fact = "Good source of Vitamin A and C";
                    protein = 2;
                    break;

                case "brocolli":
                    fname = "Broccoli";
                    calorie = 34;
                    fact = "Good source of vitamin K,\n" +
                            "  vitamin C and Folic Acid";
                    protein = 2.8;
                    break;

                case "cucumber":
                    fname = "Cucumber";
                    calorie = 15;
                    fact = "Good source of Potassium";
                    protein = 0.16;
                    break;

                case "greenApple":
                    fname = "Green Apple";
                    calorie = 52;
                    fact = "Low in Carbs";
                    protein = 0;
                    break;

                case "strawberry":
                    fname = "Strawberry";
                    calorie = 33;
                    fact = "Rich in Vitamin C";
                    protein = 0.67;
                    break;

                case "pineapple":
                    fname = "Pineapple";
                    calorie = 50;
                    fact = "Rich in Manganese";
                    protein = 0.5;
                    break;

                case "cauliflower":
                    fname = "Cauliflower";
                    calorie = 25;
                    fact = "Low in Saturated Fat and Cholesterol";
                    protein = 1.9;
                    break;

                case "butternutSquash":
                    fname = "Butternut Squash";
                    calorie = 45;
                    fact = "Good source of Potassium and Vitamin E";
                    protein = 1;
                    break;

                default:
                    break;
            }
        }
            return "Name: " + fname + "\n" + "Detected: " + num + "\n" + "Total Calorie:" + calorie * num +"\t" + "per 100gm" + "\n" + "Total Protien: " + protein * num +"\t" + "per 100gm" + "\n" + "Facts: " + fact;
        }


}
