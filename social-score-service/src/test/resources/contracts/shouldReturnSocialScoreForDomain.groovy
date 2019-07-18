package contracts

import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return even when number input is even"
    request{
        method GET()
        url("/search") {
            queryParameters {
                parameter("key", "domain")
                parameter("value","www.google.com")
            }
        }
    }
    response {
        body("""
        [{"id":"144423c1-dcd1-4c27-9d57-99386590a7da","url":"http://www.google.com/search?query=Something","score":10}] 
""")
        status 200
    }
}