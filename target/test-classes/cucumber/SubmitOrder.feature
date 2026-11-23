@tag
Feature: Submit the order from Ecommerce WebSite
I want to use this template for my feature file

  Background:
    Given I landed on Ecommerce Page
    
    
  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <name> and password <Password>
    When I add product <productName> to Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
    
    Examples:
      | name                  	| Password    | productName |
      | rahulshetty@gmail.com 	| IamKing@000 | ZARA COAT 3 |
