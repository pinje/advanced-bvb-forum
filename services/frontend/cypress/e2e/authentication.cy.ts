describe('Registration', () => {

    it('login with valid credentials', () => {

        cy.visit('/login')

        cy.get('input[type=email]').type("user@gmail.com")
        cy.get('input[type=password]').type("12345678")

        cy.get('button').contains('Login').click()

        cy.url().should('include', '/')
    })
})