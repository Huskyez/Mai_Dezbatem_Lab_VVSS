describe('My First Test', () => {
    it('clicking "en button" navigates to a new url', () => {
      cy.visit('https://www.cs.ubbcluj.ro/');
  
      cy.get('.lang-item-18').click();
  
      cy.url().should('include', '/en');
    })
  })