
describe('Scenario1', () => {
    it('clicking "en button" navigates to a new url', () => {
        cy.visit('https://app.webpagetest.org/ui/Entry/WptLogin.aspx');

        let url;
      
        cy.readFile('cypress\\integration\\InputData\\ValidLogin.json').then((inputData) => {
            cy.get('input[id="Email"]').type(inputData.username);
            cy.get('input[id="Password"]').type(inputData.password);
            cy.get('input[id="LoginButton"]').click();
            cy.get('input[id="url"]').type(inputData.url);
            url = inputData.url;
        });
        
        cy.get('select[id="location"]').select('ec2-sa-east-1-catchpoint');
        cy.get('.start_test').click();
        cy.get('li[id="testing"]').should((elem) => {
            expect(elem.text()).to.contains('Testing');
        });
        cy.url().should('include', 'result');

        cy.get('.runningDetails').find('a').first().should((elem) => {
            expect(elem.text()).to.contains(url);
        });
        
        cy.get('.test_menu', {timeout: 25000}).find('li').contains('Details').click();
        cy.url().should('include', 'details');
        cy.get('ul[id="wptAuthBar"]').find('li').first().find('a').contains('Logout').click();
        cy.get('ul[id="wptAuthBar"]').find('li').first().find('a').contains('Login').should((elem) => {
            expect(elem.text()).to.contains('Login');
        });
    })
  })