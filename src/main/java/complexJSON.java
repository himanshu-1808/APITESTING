import io.restassured.path.json.JsonPath;

public class complexJSON {
    public static void main(String[] args) {
        JsonPath js = GenericUtils.getJSONPath(payLoad.complexJson());
        //Number of courses
        int coursesCount = js.getInt("courses.size()");
        System.out.println("Count of courses present: " + coursesCount);

        //Purchase amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        //First title
        String firstCourseTitle = js.getString("courses[0].title");
        System.out.println(firstCourseTitle);

        //All title and amount
        for(int i=0;i<js.getInt("courses.size()");i++){
            String title = js.getString("courses["+i+"].title");
            String amount = js.getString("courses["+i+"].price");
            System.out.println("Title: "+title);
            System.out.println("Price: "+amount);
        }

        //Copies for RPA
        for(int i=0;i<js.getInt("courses.size()");i++){
            String title = js.getString("courses["+i+"].title");
            if(title.equalsIgnoreCase("RPA")){
                System.out.println("Copies for RPA: "+js.getInt("courses["+i+"].copies"));break;
            }
        }

        int totalAmount=0;
        //Sum equal to purchase amount
        for(int i=0;i<js.getInt("courses.size()");i++){
            int amount = js.getInt("courses["+i+"].price");
            int copies = js.getInt("courses["+i+"].copies");
            totalAmount=(amount*copies)+totalAmount;
        }

        if(totalAmount == purchaseAmount)
            System.out.println("Correct purchase amount");
        else System.out.println("Incorrect purchase amount");
    }
}


//1.Print No of courses returned by API
//        2.Print Purchase Amount
//
//        3.Print Title of the first course
//
//        4.Print All course titles and their respective Prices
//
//        5.Print no of copies sold by RPA Course
//
//        6.Verify if Sum of all Course prices matches with Purchase Amount