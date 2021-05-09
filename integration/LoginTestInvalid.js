
describe('My First Test', () => {
    it('clicking "en button" navigates to a new url', () => {
        
        cy.visit('https://app.webpagetest.org/ui/Entry/WptLogin.aspx');
      
        cy.readFile('cypress\\integration\\InputData\\InvalidLogin.json').then((inputData) => {
            cy.get('input[id="Email"]').type(inputData.username);
            cy.get('input[id="Password"]').type(inputData.password);
            cy.get('input[id="LoginButton"]').click();
        });

        cy.get('.red-box').contains('Unable to log in. Please verify the email and/or password provided.');
    })
  })