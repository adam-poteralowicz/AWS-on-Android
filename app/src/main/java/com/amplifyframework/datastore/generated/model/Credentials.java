package com.amplifyframework.datastore.generated.model;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import java.util.Objects;
import java.util.UUID;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Credentials type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Credentials", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Credentials implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField LOGIN = field("login");
  public static final QueryField PASSWORD = field("password");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String login;
  private final @ModelField(targetType="String", isRequired = true) String password;
  public String getId() {
      return id;
  }
  
  public String getLogin() {
      return login;
  }
  
  public String getPassword() {
      return password;
  }
  
  private Credentials(String id, String login, String password) {
    this.id = id;
    this.login = login;
    this.password = password;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Credentials credentials = (Credentials) obj;
      return ObjectsCompat.equals(getId(), credentials.getId()) &&
              ObjectsCompat.equals(getLogin(), credentials.getLogin()) &&
              ObjectsCompat.equals(getPassword(), credentials.getPassword());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getLogin())
      .append(getPassword())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Credentials {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("login=" + String.valueOf(getLogin()) + ", ")
      .append("password=" + String.valueOf(getPassword()))
      .append("}")
      .toString();
  }
  
  public static LoginStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Credentials justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Credentials(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      login,
      password);
  }
  public interface LoginStep {
    PasswordStep login(String login);
  }
  

  public interface PasswordStep {
    BuildStep password(String password);
  }
  

  public interface BuildStep {
    Credentials build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements LoginStep, PasswordStep, BuildStep {
    private String id;
    private String login;
    private String password;
    @Override
     public Credentials build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Credentials(
          id,
          login,
          password);
    }
    
    @Override
     public PasswordStep login(String login) {
        Objects.requireNonNull(login);
        this.login = login;
        return this;
    }
    
    @Override
     public BuildStep password(String password) {
        Objects.requireNonNull(password);
        this.password = password;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String login, String password) {
      super.id(id);
      super.login(login)
        .password(password);
    }
    
    @Override
     public CopyOfBuilder login(String login) {
      return (CopyOfBuilder) super.login(login);
    }
    
    @Override
     public CopyOfBuilder password(String password) {
      return (CopyOfBuilder) super.password(password);
    }
  }
  
}
