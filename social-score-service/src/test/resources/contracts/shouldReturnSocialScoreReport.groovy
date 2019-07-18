package contracts

import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return even when number input is even"
    request{
        method GET()
        url("/report")
    }
    response {
        body("""
        [{"domain":"www.google.com","urlCount":2,"score":20},{"domain":"www.facebook.com","urlCount":1,"score":10}] 
""")
        status 200
    }
}