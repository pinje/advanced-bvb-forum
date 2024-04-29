describe('Registration', () => {

    it('create a new user', () => {

        cy.visit('/signup')

        cy.get('input[type=email]').type("user@gmail.com")
        cy.get('input[type=text]').type("user123")
        cy.get('input[type=password]').type("12345678")

        cy.get('button').contains('Sign up').click()

        cy.url().should('include', '/login')
    })
})