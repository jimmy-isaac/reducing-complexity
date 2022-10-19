# Background
**Complexity** in software applications is of two types: *Intrinsic* and *accidental*.
As the term suggests, intrinsic complexity is part of the definition of the application and 
cannot be avoided. Accidental complexity is what should be minimized.
The reason is that the likelihood of bugs increases with complexity. Also, as complexity increases, making changes costs more, and again is prone to more bugs.

While it is important to ensure algorithmic efficiency, most accidental complexity creeps into
business applications because of run-of-the-mill business rules, not incorrect algorithm use.

One area of accidental complexity in object-oriented implementations is the use of conditional statements that pivot on string fields. When the conditional statements are littered across multiple classes, that is an illustration of accidental complexity. 
The *strategy* design pattern can be used to eliminate the conditional statements.
This project is an example of employing the strategy pattern using Java Enums as suggested by
Joshua Bloch's book *Effective Java*.

# Use case
This project is a contrived example of a grocery store, where the *products'* *quantities* and *unit sale price* can be in different units. For example, the units for quantity could be in *grams*, while the unit sale price could be in dollars per *pound*.

The project models units using Java Enum, and is used in calculating *total sale price* in dollars.

# Classes
Here is a brief explanation of the classes in this project.

**Measure** models the dimension being used when using units like kilograms and feet. While count is the most common unit of measure, even time can be a unit. 
An example of using time in business, is a training session sold and measured by the amount of time used.

**SystemOfUnits**. Multiple standards exist, each maintained by a controlling authority. The most universally used is the Metric system. In the US, the *United States Customary Units*, which, for example, uses pounds as a measure of weight. In the Metric system,  *kilogram* is a measure of weight. 1 kilogram  is approximately 2.2 pounds.

**Unit**. Defines units like kilogram, pound and feet, using Measure and SystemOfUnits.

**Product**. The product being sold. A sale price is associated with the product.

**UnitPrice**. Modeled as a dollar amount and the units. For example, if the unit is *Unit.Gram*, then the price is per gram.

**SaleLineItem**. When a product is sold, this represents each product being sold. Therefore, it copies across the unit price from the product.
It also includes the quantity being sold. The intent of this project is to show how the method *calculateNetSalePrice* uses enums to calculate the net price.

**Quantity**. Models quantity. Again like UnitPrice, this class includes a numeric quantity, and the units, which in 99% of the cases would be count, but could be a measure of weight or length. Just because 99% of the products are using count, by generalizing Quantity, the accidental complexity of conditional statements because of this is avoided.


# Caveat
This is not a full-fledged application. Hence only some of the units are defined. Also, a few test invocations are included in the SaleLineItem class, but no unit tests have been written.

