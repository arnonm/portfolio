git checkout -b TLVTrade2
git pull origin TLVTrade2
mvn -f portfolio-app/pom.xml clean verify -Ppackage-distro -T 1C
open  portfolio-product/target/products/name.abuchen.portfolio.product/macosx/cocoa/
