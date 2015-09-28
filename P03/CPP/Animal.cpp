#include "Animal.hpp"
int Animal::getIdade() {return _idade;}
void Animal::setIdade(int idade) {_idade = idade;}
std::string Animal::getNome() {return _nome;}
void Animal::setNome(std::string nome) {_nome = nome;}

std::ostream &operator<<(std::ostream &o, const Animal &animal) {
  return o << animal._nome << " (" << animal._idade << " ano" << (animal._idade == 1 ? "" : "s") << ")";
}

bool Animal::operator==(const Animal &rhs) {
  return _idade == rhs._idade && _nome == rhs._nome;
}