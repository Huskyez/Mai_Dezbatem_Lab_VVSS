
describe('Scenario2', () => {
    it('clicking "en button" navigates to a new url', () => {
        cy.visit('https://app.webpagetest.org/ui/Entry/WptLogin.aspx');
      
        cy.readFile('cypress\\integration\\InputData\\ValidLogin.json').then((inputData) => {
            cy.get('input[id="Email"]').type(inputData.username);
            cy.get('input[id="Password"]').type(inputData.password);
            cy.get('input[id="LoginButton"]').click();
        });

        cy.get('ul[id="nav"]').find('li').contains('Test History').click();
        cy.get('[data-item-index="0"]').find('div').first().find('div').first().click({force: true});
        cy.get('[data-item-index="1"]').find('div').first().find('div').first().click({force: true});
        cy.get('button[data-testid="button"]').contains('Compare').click();
        cy.url().should('include', 'compare.php');
        cy.get('ul[id="wptAuthBar"]').find('li').first().find('a').contains('Logout').click();
        cy.get('ul[id="wptAuthBar"]').find('li').first().find('a').contains('Login').should((elem) => {
            expect(elem.text()).to.contains('Login');
        });;
    })
  })