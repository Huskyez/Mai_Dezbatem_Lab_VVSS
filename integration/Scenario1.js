
describe('My First Test', () => {
    it('clicking "en button" navigates to a new url', () => {
        cy.visit('https://app.webpagetest.org/ui/Entry/WptLogin.aspx');
      
        cy.readFile('cypress\\integration\\InputData\\ValidLogin.json').then((inputData) => {
            cy.get('input[id="Email"]').type(inputData.username);
            cy.get('input[id="Password"]').type(inputData.password);
            cy.get('input[id="LoginButton"]').click();
        });

        cy.get('input[id="url"]').type('www.google.com');
        cy.get('select[id="location"]').select('ec2-sa-east-1-catchpoint');
        cy.get('.start_test').click();
      
    })
  })