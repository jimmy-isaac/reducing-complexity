# Background
This project is a follow-up to project ReducingComplexity1. For details, please see the README in that project.

# Use case
This project adds a **Company** to the business model, where the products are shared between companies. For example, the companies could be part of a merger. Carrying on the use case of having units of measure defined for a product, each company can have different primary and optional secondary System of Units. An example usage is to display quantities in the primary and secondary units of the company selling the product. 

# New classes
Here is a brief explanation of the classes in this project.

**Company** models a company/organization, in an environment, where the software is used by multiple companies, with shared products.

**Sale** is a container for *SaleLineItem*. Contains information that is common to all *Sale Line Items*. In this project, these are *Company*, and *SaleType* with values Online or Physical.

# Functionality of interest
The method *getQtyInPrimaryReferenceUnits* computes the quantity using the default primary System of Units defined for the company making the sale.
Also, assuming that there is no shipping and handling cost for physical sale, the *SaleType* computes the shipping costs in the case of online sales.


# Conclusion
In this project, 3 types that involve business rules are modeled to avoid using conditional statements, with the larger goal of  reducing accidental complexity.

# Next Steps
Is there some pattern that can be abstracted and enforced to further reduce complexity?

# Caveat
This is not a full-fledged application. Hence only some of the instances for Enums like Unit and Company are defined. Also, for now, unit tests have not yet been written.
